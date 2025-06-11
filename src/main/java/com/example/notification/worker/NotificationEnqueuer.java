package com.example.notification.worker;

import com.example.notification.model.Notification;
import com.example.notification.model.NotificationStatus;
import com.example.notification.queue.NotificationQueue;
import com.example.notification.repository.NotificationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotificationEnqueuer {

    private final NotificationRepository repository;
    private final NotificationQueue queue;

    public NotificationEnqueuer(NotificationRepository repository, NotificationQueue queue) {
        this.repository = repository;
        this.queue = queue;
    }

    @Scheduled(fixedRate = 60000) // every 60 seconds
    public void enqueueUpcoming() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next5Min = now.plusMinutes(5);

        List<Notification> dueSoon = repository.findByStatusAndSendAtBetween(
                NotificationStatus.SCHEDULED, now, next5Min
        );

        for (Notification n : dueSoon) {
            queue.enqueue(n);
        }
    }
}