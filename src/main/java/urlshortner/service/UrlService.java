package urlshortner.service;

import urlshortner.dta.UrlRequest;
import urlshortner.dta.UrlResponse;
import urlshortner.entity.UrlMapping;
import urlshortner.exception.UrlShortenerException;
import urlshortner.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Value("${app.base-url}")
    private String baseUrl;

    private String generateShortCode() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder shortCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            shortCode.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortCode.toString();
    }

    public UrlResponse createShortUrl(UrlRequest request) {
        String shortCode = null;
        int maxAttempts = 5;

        for (int i = 0; i < maxAttempts; i++) {
            String candidate = generateShortCode();
            if (!urlRepository.existsByShortCode(candidate)) {
                shortCode = candidate;
                break;
            }
        }

        if (shortCode == null) {
            throw new UrlShortenerException(
                "Could not generate a unique short code. Please try again.",
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(request.getOriginalUrl());
        urlMapping.setShortCode(shortCode);
        urlMapping.setCreatedAt(LocalDateTime.now());
        urlMapping.setClickCount(0L);

        if (request.getValidityDays() > 0) {
            urlMapping.setExpiresAt(LocalDateTime.now().plusDays(request.getValidityDays()));
        }

        urlRepository.save(urlMapping);
        return mapToResponse(urlMapping);
    }

    public UrlResponse getOriginalUrl(String shortCode) {
        Optional<UrlMapping> urlMapping = urlRepository.findByShortCode(shortCode);

        if (urlMapping.isEmpty()) {
            throw new UrlShortenerException("Short URL not found", HttpStatus.NOT_FOUND);
        }

        UrlMapping mapping = urlMapping.get();

        if (mapping.getExpiresAt() != null && mapping.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new UrlShortenerException("Short URL has expired", HttpStatus.GONE);
        }

        mapping.setClickCount(mapping.getClickCount() + 1);
        urlRepository.save(mapping);
        return mapToResponse(mapping);
    }

    public UrlResponse getStats(String shortCode) {
        Optional<UrlMapping> urlMapping = urlRepository.findByShortCode(shortCode);

        if (urlMapping.isEmpty()) {
            throw new UrlShortenerException("Short URL not found", HttpStatus.NOT_FOUND);
        }

        return mapToResponse(urlMapping.get());
    }

    public void deleteUrl(String shortCode) {
        Optional<UrlMapping> urlMapping = urlRepository.findByShortCode(shortCode);

        if (urlMapping.isEmpty()) {
            throw new UrlShortenerException("Short URL not found", HttpStatus.NOT_FOUND);
        }

        urlRepository.delete(urlMapping.get());
    }

    private UrlResponse mapToResponse(UrlMapping urlMapping) {
        UrlResponse response = new UrlResponse();
        response.setOriginalUrl(urlMapping.getOriginalUrl());
        response.setShortCode(urlMapping.getShortCode());
        response.setShortUrl(baseUrl + "/r/" + urlMapping.getShortCode());
        response.setCreatedAt(urlMapping.getCreatedAt());
        response.setExpiresAt(urlMapping.getExpiresAt());
        response.setClickCount(urlMapping.getClickCount());
        return response;
    }
}