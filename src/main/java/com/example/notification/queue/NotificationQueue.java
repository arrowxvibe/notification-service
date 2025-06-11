package com.example.notification.queue;

import com.example.notification.model.Notification;

public interface NotificationQueue {
    void enqueue(Notification notification);
    Notification dequeue();
}
