package com.example.notification.processor;

import com.example.notification.model.Notification;
import com.example.notification.template.TemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailNotificationSender implements NotificationSender {

    private final TemplateEngine engine;

    public EmailNotificationSender(TemplateEngine engine) {
        this.engine = engine;
    }

    @Override
    public void send(Notification notification) {
        String subject = engine.render(notification.getSubjectTemplate(), notification.getPlaceholderValues());
        String body = engine.render(notification.getBodyTemplate(), notification.getPlaceholderValues());
        // TODO : have your own solution/integration if applicable
        try {
            Thread.sleep(2000); // simulate delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("[EMAIL] To: {} | Subject: {} | Body: {}",
                notification.getRecipient(), subject, body);
    }
}
