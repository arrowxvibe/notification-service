package com.example.notification.config;

import com.example.notification.model.NotificationStatus;
import com.example.notification.model.Notification;
import com.example.notification.queue.NotificationQueue;
import com.example.notification.repository.NotificationRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class NotificationPreloader {

    private final NotificationRepository repository;
    private final NotificationQueue queue;

    public NotificationPreloader(NotificationRepository repository, NotificationQueue queue) {
        this.repository = repository;
        this.queue = queue;
    }

    @PostConstruct
    public void preload() {
        List<Notification> preloadList = repository
                .findByStatusAndSendAtBefore(NotificationStatus.SCHEDULED, LocalDateTime.now().plusMinutes(5));
        preloadList.forEach(queue::enqueue);
        log.info("Preloaded {} notifications into Notification Queue.", preloadList.size());
    }
}
