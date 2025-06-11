package com.example.notification.template;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TemplateEngine {

    public String render(String template, Map<String, String> placeholders) {
        if (template == null || placeholders == null) return template;

        String result = template;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String key = "{{" + entry.getKey() + "}}";
            result = result.replace(key, entry.getValue());
        }
        return result;
    }
}
