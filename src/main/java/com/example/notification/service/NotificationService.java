package com.example.notification.service;

import com.example.notification.dto.NotificationRequest;
import com.example.notification.model.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationService {

    Notification getNotification(UUID id);

    Notification updateNotification(Notification notification);

    void save(Notification notification);

    void schedule(NotificationRequest request);

    void deleteNotification(UUID id);

    List<Notification> getDueScheduledNotifications();

    List<Notification> getAllNotifications();
}