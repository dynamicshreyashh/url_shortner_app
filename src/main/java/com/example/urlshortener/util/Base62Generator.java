package com.example.urlshortener.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class Base62Generator {

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int DEFAULT_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();

    public String generate() {
        return generate(DEFAULT_LENGTH);
    }

    public String generate(int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(BASE62.charAt(random.nextInt(BASE62.length())));
        }
        return builder.toString();
    }
}
