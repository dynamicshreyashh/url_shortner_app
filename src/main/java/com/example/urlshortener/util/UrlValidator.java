package com.example.urlshortener.util;

import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class UrlValidator {

    public boolean isValid(String url) {
        try {
            URI parsed = URI.create(url);
            return parsed.getScheme() != null
                    && ("http".equalsIgnoreCase(parsed.getScheme()) || "https".equalsIgnoreCase(parsed.getScheme()))
                    && parsed.getHost() != null;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
