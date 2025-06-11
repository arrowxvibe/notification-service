package com.example.notification.processor;

import com.example.notification.model.NotificationType;
import org.springframework.stereotype.Component;

@Component
public class NotificationSenderFactory {

    private final EmailNotificationSender emailSender;
    private final PushNotificationSender pushSender;
    private final MobileNotificationSender mobileSender;

    public NotificationSenderFactory(
            EmailNotificationSender emailSender,
            PushNotificationSender pushSender,
            MobileNotificationSender mobileSender
    ) {
        this.emailSender = emailSender;
        this.pushSender = pushSender;
        this.mobileSender = mobileSender;
    }

    public NotificationSender getSender(NotificationType type) {
        return switch (type) {
            case EMAIL_NOTIFICATION -> emailSender;
            case PUSH_NOTIFICATION -> pushSender;
            case MOBILE -> mobileSender;
        };
    }
}
