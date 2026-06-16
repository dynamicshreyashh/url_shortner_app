package urlshortner.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class UrlShortenerException extends RuntimeException {

    private final HttpStatus status;

    public UrlShortenerException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}