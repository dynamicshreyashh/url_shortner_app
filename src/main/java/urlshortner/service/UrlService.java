package urlshortner.service;

import urlshortner.dta.UrlRequest;
import urlshortner.dta.UrlResponse;
import urlshortner.entity.UrlMapping;
import urlshortner.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    // Generate short code
    private String generateShortCode(){
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder shortCode = new StringBuilder();
        for(int i = 0; i < 6; i++){
            shortCode.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortCode.toString();
    }

    // Create short URL
    public UrlResponse createShortUrl(UrlRequest request){
        String shortCode;
        do{
            shortCode = generateShortCode();
        } while (urlRepository.existsByShortCode(shortCode));

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(request.getOriginalUrl());
        urlMapping.setShortCode(shortCode);
        urlMapping.setCreatedAt(LocalDateTime.now());
        urlMapping.setClickCount(0L);

        if(request.getValidityDays() > 0){
            urlMapping.setExpiresAt(LocalDateTime.now().plusDays(request.getValidityDays()));
        }

        urlRepository.save(urlMapping);

        return mapToResponse(urlMapping);
    }

    // Get Original URL from short code
    public UrlResponse getOriginalUrl(String shortCode){
        Optional<UrlMapping> urlMapping = urlRepository.findByShortCode(shortCode);

        if(urlMapping.isEmpty()){
            throw new RuntimeException("Short URl not found");
        }

        UrlMapping mapping = urlMapping.get();

        // Check if URL is expired
        if(mapping.getExpiresAt() != null && mapping.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Short URl has expired");
        }

        // Increase click count
        mapping.setClickCount(mapping.getClickCount() + 1);
        urlRepository.save(mapping);
        return mapToResponse(mapping);
    }

    // Get status of a short URL
    public UrlResponse getStats(String shortCode){
        Optional<UrlMapping> urlMapping = urlRepository.findByShortCode(shortCode);

        if(urlMapping.isEmpty()){
            throw new RuntimeException("Short URL not found");
        }

        return mapToResponse(urlMapping.get());
    }

    // Delete a short URl
    public void deleteUrl(String shortCode){
        Optional<UrlMapping> urlMapping = urlRepository.findByShortCode(shortCode);

        if(urlMapping.isEmpty()){
            throw new RuntimeException("Short URL not found");
        }

        urlRepository.delete(urlMapping.get());
    }

    // Convert UrlMapping to UrlResponse
    private UrlResponse mapToResponse(UrlMapping urlMapping){
        UrlResponse response = new UrlResponse();
        response.setOriginalUrl(urlMapping.getOriginalUrl());
        response.setShortCode(urlMapping.getShortCode());

        response.setShortUrl(
                "https://url-shortner-app-wwja.onrender.com/r/"
                        + urlMapping.getShortCode()
        );
        response.setCreatedAt(urlMapping.getCreatedAt());
        response.setExpiresAt(urlMapping.getExpiresAt());
        response.setClickCount(urlMapping.getClickCount());
        return response;
    }
}
