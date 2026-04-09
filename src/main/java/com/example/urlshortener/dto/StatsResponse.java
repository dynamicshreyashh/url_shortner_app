package com.example.urlshortener.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class StatsResponse {
    private String originalUrl;
    private Long clicks;
    private LocalDateTime createdAt;
}
