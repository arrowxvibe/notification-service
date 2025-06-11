
### Sample CURL - send instant notification

```curl
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
    },
  }'
```
### Sample CURL - send instant notification, in repeat mode

```curl
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
    },
    "recurrence": "every 1 minute"
  }'
```
### Sample CURL - send scheduled notification
```curl
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
    },
    "sendAt": "2025-06-11T20:57:00",
  }'
```
### Sample CURL - send notification, in repeat mode, with start date, end date
```curl
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
    },
    "sendAt": "2025-06-11T20:57:00",
    "sendAt": "2026-06-11T20:57:00",
    "recurrence": "every 1 day"
  }'
```