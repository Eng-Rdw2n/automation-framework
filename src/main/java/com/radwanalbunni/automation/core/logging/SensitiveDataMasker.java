package com.radwanalbunni.automation.core.logging;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Masks sensitive values before they are written to logs or test reports.
 */
public final class SensitiveDataMasker {

    private static final Pattern EMAIL = Pattern.compile(
            "(?i)(?<![\\w.+-])([\\w.+-]+)@([\\w.-]+\\.[a-z]{2,})(?![\\w.-])"
    );
    private static final Pattern SENSITIVE_JSON_FIELD = Pattern.compile(
            "(?i)(\"(?:email|password|token|authorization|card_number|cvv|cvc|mobile_number|address1|address2|zipcode|first_name|last_name|birth_day|birth_month|birth_year|company|state|city)\"\\s*:\\s*\")([^\"]*)(\")"
    );

    private SensitiveDataMasker() {
    }

    public static String maskEmail(String email) {
        if (email == null || email.isBlank()) {
            return "<empty>";
        }

        int atIndex = email.indexOf('@');
        if (atIndex <= 0 || atIndex == email.length() - 1) {
            return "***";
        }

        return email.charAt(0) + "***" + email.substring(atIndex);
    }

    public static String sanitizeJson(String content) {
        if (content == null || content.isBlank()) {
            return content;
        }

        Matcher matcher = SENSITIVE_JSON_FIELD.matcher(content);
        StringBuilder sanitized = new StringBuilder();
        while (matcher.find()) {
            String field = matcher.group(1);
            String value = matcher.group(2);
            String maskedValue = field.toLowerCase().contains("email")
                    ? maskEmail(value)
                    : "***";
            matcher.appendReplacement(
                    sanitized,
                    Matcher.quoteReplacement(field + maskedValue + matcher.group(3))
            );
        }
        matcher.appendTail(sanitized);
        return sanitized.toString();
    }

    public static String sanitizeText(String content) {
        if (content == null || content.isBlank()) {
            return content;
        }

        Matcher matcher = EMAIL.matcher(content);
        StringBuilder sanitized = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(
                    sanitized,
                    Matcher.quoteReplacement(maskEmail(matcher.group()))
            );
        }
        matcher.appendTail(sanitized);
        return sanitized.toString();
    }
}
