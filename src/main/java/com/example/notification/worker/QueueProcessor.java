package com.example.notification.worker;

import com.example.notification.dto.Recurrence;
import com.example.notification.model.Notification;
import com.example.notification.model.NotificationStatus;
import com.example.notification.processor.NotificationSenderFactory;
import com.example.notification.queue.NotificationQueue;
import com.example.notification.service.NotificationService;
import com.example.notification.service.RecurrenceParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
public class QueueProcessor {

    private final NotificationQueue queue;
    private final NotificationSenderFactory senderFactory;
    private final NotificationService service;
    private final RecurrenceParser recurrenceParser;

    public QueueProcessor(NotificationQueue queue,
                          NotificationSenderFactory senderFactory,
                          NotificationService service,
                          RecurrenceParser recurrenceParse) {
        this.queue = queue;
        this.senderFactory = senderFactory;
        this.service = service;
        this.recurrenceParser = recurrenceParse;
    }

    @Scheduled(fixedRate = 100)
    public void processQueue() {
        Notification notification;
        while ((notification = queue.dequeue()) != null) {
            if (notification.getSendAt().isAfter(LocalDateTime.now())) {
                // Not time yet — re-enqueue
                queue.enqueue(notification);
                break; // exit this round to avoid tight loop
            }

            process(notification);
        }
    }

    private void process(Notification notification) {
        try {
            senderFactory.getSender(notification.getType()).send(notification);
            notification.setStatus(NotificationStatus.SENT);
            if (notification.getRecurrence() != null && !notification.getRecurrence().isBlank()) {
                Recurrence recurrence = recurrenceParser.parse(notification.getRecurrence());
                LocalDateTime nextSendAt = recurrence.getNextOccurrence(notification.getSendAt());
                if (notification.getEndAt() == null || nextSendAt.isBefore(notification.getEndAt())) {
                    Notification next = new Notification();
                    next.setId(UUID.randomUUID());
                    next.setType(notification.getType());
                    next.setRecipient(notification.getRecipient());
                    next.setSubjectTemplate(notification.getSubjectTemplate());
                    next.setBodyTemplate(notification.getBodyTemplate());
                    next.setPlaceholderValues(notification.getPlaceholderValues());
                    next.setSendAt(nextSendAt);
                    next.setRecurrence(notification.getRecurrence());
                    next.setEndAt(notification.getEndAt());
                    next.setStatus(NotificationStatus.SCHEDULED);
                    service.save(next);
                    if (nextSendAt.isBefore(LocalDateTime.now().plusMinutes(5))) {
                        queue.enqueue(next);
                    }
                }
            }
        } catch (Exception e) {
            notification.setStatus(NotificationStatus.FAILED);
            log.error("Notification {}: {}", notification.getId(), e.getMessage());
        } finally {
            service.save(notification);
        }
    }
}