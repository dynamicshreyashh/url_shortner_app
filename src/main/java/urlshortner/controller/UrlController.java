package urlshortner.controller;

import urlshortner.dta.UrlRequest;
import urlshortner.dta.UrlResponse;
import urlshortner.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/api/shorten")
    public ResponseEntity<UrlResponse> shortenUrl(@Valid @RequestBody UrlRequest request) {
        UrlResponse response = urlService.createShortUrl(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/r/{shortCode}")
    public void redirectUrl(@PathVariable String shortCode,
                            HttpServletResponse response) throws IOException {
        UrlResponse urlResponse = urlService.getOriginalUrl(shortCode);
        response.sendRedirect(urlResponse.getOriginalUrl());
    }

    @GetMapping("/api/stats/{shortCode}")
    public ResponseEntity<UrlResponse> getStats(@PathVariable String shortCode) {
        UrlResponse response = urlService.getStats(shortCode);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/delete/{shortCode}")
    public ResponseEntity<String> deleteUrl(@PathVariable String shortCode) {
        urlService.deleteUrl(shortCode);
        return ResponseEntity.ok("Short URL deleted successfully");
    }
}