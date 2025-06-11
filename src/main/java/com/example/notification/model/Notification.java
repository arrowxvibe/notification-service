package com.example.notification.model;

import com.example.notification.utils.MapToJsonConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Data
public class Notification {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String recipient;

    private String subjectTemplate;
    private String bodyTemplate;

    @Convert(converter = MapToJsonConverter.class)
    private Map<String, String> placeholderValues;

    private LocalDateTime sendAt;

    private String recurrence;

    private LocalDateTime endAt;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;
}