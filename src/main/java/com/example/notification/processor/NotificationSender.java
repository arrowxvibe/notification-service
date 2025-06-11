package com.example.notification.processor;

import com.example.notification.model.Notification;

public interface NotificationSender {
    void send(Notification notification);
}
