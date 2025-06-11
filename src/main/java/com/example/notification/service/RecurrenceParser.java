package com.example.notification.service;

import com.example.notification.dto.Recurrence;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
public class RecurrenceParser {

    public Recurrence parse(String input) {
        if (input == null || input.isBlank()) return null;
        String[] parts = input.trim().toLowerCase().split(" ");
        if (parts.length != 3 || !parts[0].equals("every")) return null;

        int value = Integer.parseInt(parts[1]);
        String unitStr = parts[2];

        return switch (unitStr) {
            case "minute", "minutes" -> new Recurrence(value, ChronoUnit.MINUTES);
            case "hour", "hours" -> new Recurrence(value, ChronoUnit.HOURS);
            case "day", "days" -> new Recurrence(value, ChronoUnit.DAYS);
            case "week", "weeks" -> new Recurrence(value, ChronoUnit.WEEKS);
            case "month", "months" -> new Recurrence(value, ChronoUnit.MONTHS);
            case "year", "years" -> new Recurrence(value, ChronoUnit.YEARS);
            default -> throw new IllegalArgumentException("Unsupported time unit: " + unitStr);
        };
    }
}