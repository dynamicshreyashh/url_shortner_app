package com.example.urlshortener.controller;

import com.example.urlshortener.dto.ShortenUrlRequest;
import com.example.urlshortener.dto.ShortenUrlResponse;
import com.example.urlshortener.dto.StatsResponse;
import com.example.urlshortener.entity.ShortUrl;
import com.example.urlshortener.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @PostMapping("/api/shorten")
    public ResponseEntity<ShortenUrlResponse> createShortUrl(
            @Valid @RequestBody ShortenUrlRequest request,
            HttpServletRequest httpServletRequest
    ) {
        String baseUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort();
        ShortenUrlResponse response = urlShortenerService.createShortUrl(request, baseUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        ShortUrl shortUrl = urlShortenerService.resolveAndTrack(code);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(shortUrl.getOriginalUrl()));
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }

    @GetMapping("/api/stats/{code}")
    public ResponseEntity<StatsResponse> getStats(@PathVariable String code) {
        return ResponseEntity.ok(urlShortenerService.getStats(code));
    }
}
