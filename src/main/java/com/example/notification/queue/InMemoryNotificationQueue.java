package com.example.notification.queue;

import com.example.notification.model.Notification;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class InMemoryNotificationQueue implements NotificationQueue {

    private final Queue<Notification> queue = new ConcurrentLinkedQueue<>();
    private final Set<UUID> queuedIds = ConcurrentHashMap.newKeySet();

    @Override
    public void enqueue(Notification notification) {
        UUID id = notification.getId();
        if (queuedIds.add(id)) {
            queue.offer(notification);
        }
    }

    @Override
    public Notification dequeue() {
        Notification notification = queue.poll();
        if (notification != null) {
            queuedIds.remove(notification.getId());
        }
        return notification;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}