package com.example.notification.dto;

import com.example.notification.model.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private NotificationType type;
    private String recipient;
    private Template template;
    private Map<String, String> placeholders;
    private LocalDateTime sendAt;
    private String recurrence; // e.g., "every 2 hours"
    private LocalDateTime endAt;
}