package com.example.notification.repository;

import com.example.notification.model.Notification;
import com.example.notification.model.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findByStatusAndSendAtBefore(NotificationStatus status, LocalDateTime time);
    List<Notification> findByStatusAndSendAtBetween(
            NotificationStatus status,
            LocalDateTime start,
            LocalDateTime end
    );
}
