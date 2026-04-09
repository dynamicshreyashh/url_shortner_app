package com.example.urlshortener.service;

import com.example.urlshortener.dto.ShortenUrlRequest;
import com.example.urlshortener.dto.ShortenUrlResponse;
import com.example.urlshortener.dto.StatsResponse;
import com.example.urlshortener.entity.ShortUrl;

public interface UrlShortenerService {
    ShortenUrlResponse createShortUrl(ShortenUrlRequest request, String baseUrl);

    ShortUrl resolveAndTrack(String code);

    StatsResponse getStats(String code);
}
