# 📬 Notification Service

A lightweight, extensible notification scheduling system using **Spring Boot**.

---

## 🧪 Sample Use Cases

### ✅ Instant Notification

```bash
curl --location 'http://localhost:8080/api/notifications' \
--header 'Content-Type: application/json' \
--data-raw '{
  "type": "EMAIL_NOTIFICATION",
  "recipient": "user@example.com",
  "template": {
    "subject": "Meeting Reminder for {{name}}",
    "body": "Hi {{name}}, your meeting is scheduled at {{time}}."
  },
  "placeholders": {
    "name": "Alice",
    "time": "3:00 PM"
  }
}'
```
### 🔁 Recurring Notification
```bash
curl --location 'http://localhost:8080/api/notifications' \
--header 'Content-Type: application/json' \
--data-raw '{
  "type": "EMAIL_NOTIFICATION",
  "recipient": "user@example.com",
  "template": {
    "subject": "Daily Reminder for {{name}}",
    "body": "Hello {{name}}, this is your daily reminder."
  },
  "placeholders": {
    "name": "Alice"
  },
  "recurrence": "every 1 day"
}'
```
### ⏳ Scheduled Notification
```bash
curl --location 'http://localhost:8080/api/notifications' \
--header 'Content-Type: application/json' \
--data-raw '{
  "type": "EMAIL_NOTIFICATION",
  "recipient": "user@example.com",
  "template": {
    "subject": "Scheduled Notification for {{name}}",
    "body": "Hi {{name}}, this is your scheduled message."
  },
  "placeholders": {
    "name": "Alice"
  },
  "sendAt": "2025-06-11T20:57:00"
}'
```
### 🔁 Recurring Notification with Start & End Time
```bash
curl --location 'http://localhost:8080/api/notifications' \
--header 'Content-Type: application/json' \
--data-raw '{
  "type": "EMAIL_NOTIFICATION",
  "recipient": "user@example.com",
  "template": {
    "subject": "Periodic Notification for {{name}}",
    "body": "Hi {{name}}, this message will repeat."
  },
  "placeholders": {
    "name": "Alice"
  },
  "sendAt": "2025-06-11T20:57:00",
  "endAt": "2026-06-11T20:57:00",
  "recurrence": "every 1 week"
}'
```
### ⏱️ Recurrence Format

Use the format:

```md
"recurrence": "every <number> <unit>" 
```

Supported Examples:
```md
	•	"every 1 minute"
	•	"every 15 minutes"
	•	"every 1 hour"
	•	"every 6 hours"
	•	"every 1 day"
	•	"every 2 days"
	•	"every 1 week"
	•	"every 3 weeks"
	•	"every 1 month"
	•	"every 3 months"
	•	"every 1 year"
```
Units can be in singular or plural form (minute or minutes, etc.).

⚠️ Note: All notifications are currently only logged. You must plug in your actual delivery logic (SMTP, FCM, etc.) as per your project needs.

