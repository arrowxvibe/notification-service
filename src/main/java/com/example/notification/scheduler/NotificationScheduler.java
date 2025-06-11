package com.example.notification.scheduler;

import com.example.notification.dto.Recurrence;
import com.example.notification.model.Notification;
import com.example.notification.model.NotificationStatus;
import com.example.notification.queue.NotificationQueue;
import com.example.notification.repository.NotificationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class NotificationScheduler {

    private final NotificationRepository repository;
    private final NotificationQueue queue;

    public NotificationScheduler(NotificationRepository repository, NotificationQueue queue) {
        this.repository = repository;
        this.queue = queue;
    }

    @Scheduled(fixedRate = 100)
    public void scheduleNotifications() {
        List<Notification> due = repository.findByStatusAndSendAtBefore(NotificationStatus.SCHEDULED, LocalDateTime.now());
        for (Notification n : due) {
            queue.enqueue(n);
        }
    }

    public LocalDateTime getNextTrigger(LocalDateTime from, Recurrence recurrence) {
        return switch (recurrence.getUnit()) {
            case NANOS     -> from.plusNanos(recurrence.getInterval());
            case MICROS    -> from.plusNanos(recurrence.getInterval() * 1000L); // 1 micro = 1000 nano
            case MILLIS    -> from.plus(recurrence.getInterval(), ChronoUnit.MILLIS);
            case SECONDS   -> from.plusSeconds(recurrence.getInterval());
            case MINUTES   -> from.plusMinutes(recurrence.getInterval());
            case HOURS     -> from.plusHours(recurrence.getInterval());
            case HALF_DAYS -> from.plusHours(recurrence.getInterval() * 12L);
            case DAYS      -> from.plusDays(recurrence.getInterval());
            case WEEKS     -> from.plusWeeks(recurrence.getInterval());
            case MONTHS    -> from.plusMonths(recurrence.getInterval());
            case YEARS     -> from.plusYears(recurrence.getInterval());

            // Rare/unsupported for notification system – explicitly rejected
            case DECADES, CENTURIES, MILLENNIA, ERAS, FOREVER ->
                    throw new UnsupportedOperationException("Unsupported time unit: " + recurrence.getUnit());
        };
    }}
