package urlshortner.dta;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UrlResponse {

    private String originalUrl;
    private String shortUrl;
    private String shortCode;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Long clickCount;
}
