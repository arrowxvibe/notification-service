package com.example.notification.processor;

import com.example.notification.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MobileNotificationSender implements NotificationSender {

    @Override
    public void send(Notification notification) {
        try {
            Thread.sleep(2000); // simulate delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // TODO : have your own solution/integration if applicable
        log.info("[MOBILE] To: {} | Msg: {}",
                notification.getRecipient(), notification.getBodyTemplate());
    }
}
