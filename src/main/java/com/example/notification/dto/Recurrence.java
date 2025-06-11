package com.example.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@AllArgsConstructor
public class Recurrence {
    private int interval;
    private ChronoUnit unit;

    public LocalDateTime getNextOccurrence(LocalDateTime current) {
        return current.plus(interval, unit);
    }
}