package com.example.notification.controller;

import com.example.notification.dto.NotificationRequest;
import com.example.notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Note that exposing APIs over network is risky for production env,
 * only those that are absolutely needed should be included.
 * This is used only for my own reference to debug flows and test,
 * perhaps, the schedule method will be only of use
 * for higher env.
 * */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<String> scheduleNotification(@RequestBody NotificationRequest request) {
        notificationService.schedule(request);
        return ResponseEntity.ok("Notification scheduled.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNotification(@PathVariable UUID id) {
        var notification = notificationService.getNotification(id);
        return notification != null ? ResponseEntity.ok(notification)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNotification(@PathVariable UUID id,
                                                @RequestBody NotificationRequest request) {
        var existing = notificationService.getNotification(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        // Update relevant fields from the request
        existing.setType(request.getType());
        existing.setRecipient(request.getRecipient());
        existing.setSubjectTemplate(request.getTemplate().getSubject());
        existing.setBodyTemplate(request.getTemplate().getBody());
        existing.setPlaceholderValues(request.getPlaceholders());
        existing.setSendAt(request.getSendAt());
        existing.setEndAt(request.getEndAt());
        existing.setRecurrence(request.getRecurrence());

        return ResponseEntity.ok(notificationService.updateNotification(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable UUID id) {
        var existing = notificationService.getNotification(id);
        if (existing != null) {
            notificationService.deleteNotification(id);
            return ResponseEntity.ok("Notification deleted.");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/due")
    public ResponseEntity<?> getDueNotifications() {
        return ResponseEntity.ok(notificationService.getDueScheduledNotifications());
    }

    @GetMapping
    public ResponseEntity<?> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }
}