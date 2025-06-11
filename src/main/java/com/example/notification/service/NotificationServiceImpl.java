package com.example.notification.service;

import com.example.notification.dto.NotificationRequest;
import com.example.notification.model.Notification;
import com.example.notification.model.NotificationStatus;
import com.example.notification.queue.NotificationQueue;
import com.example.notification.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final NotificationQueue notificationQueue;

    public NotificationServiceImpl(NotificationRepository repository,
                               NotificationQueue notificationQueue) {
        this.repository = repository;
        this.notificationQueue = notificationQueue;
    }

    @Override
    public Notification getNotification(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Notification updateNotification(Notification notification) {
        return repository.save(notification);
    }

    @Override
    public void save(Notification notification) {
        repository.save(notification);
    }

    @Override
    public List<Notification> getDueScheduledNotifications() {
        return repository.findByStatusAndSendAtBefore(NotificationStatus.SCHEDULED, LocalDateTime.now());
    }

    @Override
    public void deleteNotification(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return repository.findAll();
    }

    @Override
    public void schedule(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setId(UUID.randomUUID());
        notification.setType(request.getType());
        notification.setRecipient(request.getRecipient());
        notification.setSubjectTemplate(request.getTemplate().getSubject());
        notification.setBodyTemplate(request.getTemplate().getBody());
        notification.setPlaceholderValues(request.getPlaceholders());
        if (request.getSendAt() == null) {
            notification.setSendAt(LocalDateTime.now());
        } else {
            if (!request.getSendAt().isAfter(LocalDateTime.now())) {
                throw new IllegalArgumentException("`sendAt` must be in the future.");
            }
            // sample in postman -     "sendAt": "2025-06-11T20:57:00",
            notification.setSendAt(request.getSendAt());
        }
        notification.setStatus(NotificationStatus.SCHEDULED);
        notification.setRecurrence(request.getRecurrence());
        if (request.getEndAt() != null) {
            if (!request.getEndAt().isAfter(LocalDateTime.now())) {
                throw new IllegalArgumentException("`endAt` must be in the future and after the `sendAt`. use null if it's continuous");
            }
        }
        notification.setEndAt(request.getEndAt());
        repository.save(notification);
        if (notification.getSendAt().isBefore(LocalDateTime.now().plusMinutes(5))) {
            notificationQueue.enqueue(notification);
        } else {
            log.info("[DELAYED] Notification {} is scheduled after 5 minutes. Skipping for now.",
                    notification.getId());
        }
    }}