package com.example.urlshortener.service;

import com.example.urlshortener.dto.ShortenUrlRequest;
import com.example.urlshortener.dto.ShortenUrlResponse;
import com.example.urlshortener.dto.StatsResponse;
import com.example.urlshortener.entity.ShortUrl;
import com.example.urlshortener.exception.DuplicateAliasException;
import com.example.urlshortener.exception.InvalidUrlException;
import com.example.urlshortener.exception.ResourceNotFoundException;
import com.example.urlshortener.repository.ShortUrlRepository;
import com.example.urlshortener.util.Base62Generator;
import com.example.urlshortener.util.UrlValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private static final int MAX_RANDOM_ATTEMPTS = 10;

    private final ShortUrlRepository repository;
    private final Base62Generator base62Generator;
    private final UrlValidator urlValidator;

    @Override
    public ShortenUrlResponse createShortUrl(ShortenUrlRequest request, String baseUrl) {
        if (!urlValidator.isValid(request.getUrl())) {
            throw new InvalidUrlException("Invalid URL. Only http/https URLs are allowed.");
        }

        String requestedAlias = request.getAlias();
        String code = StringUtils.hasText(requestedAlias)
                ? requestedAlias.trim()
                : generateUniqueCode();

        if (StringUtils.hasText(requestedAlias) && repository.existsByShortCode(code)) {
            throw new DuplicateAliasException("Alias already exists. Choose another alias.");
        }

        ShortUrl saved = repository.save(ShortUrl.builder()
                .originalUrl(request.getUrl())
                .shortCode(code)
                .build());

        return new ShortenUrlResponse(baseUrl + "/" + saved.getShortCode());
    }

    @Override
    @Transactional
    public ShortUrl resolveAndTrack(String code) {
        ShortUrl entity = repository.findByShortCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Short URL not found."));

        entity.setClickCount(entity.getClickCount() + 1);
        return entity;
    }

    @Override
    public StatsResponse getStats(String code) {
        ShortUrl entity = repository.findByShortCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Short URL not found."));

        return StatsResponse.builder()
                .originalUrl(entity.getOriginalUrl())
                .clicks(entity.getClickCount())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private String generateUniqueCode() {
        for (int i = 0; i < MAX_RANDOM_ATTEMPTS; i++) {
            int length = 6 + i % 3;
            String candidate = base62Generator.generate(length);
            if (!repository.existsByShortCode(candidate)) {
                return candidate;
            }
        }
        throw new IllegalStateException("Could not generate unique short code.");
    }
}
