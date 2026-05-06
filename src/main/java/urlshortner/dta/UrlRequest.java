package urlshortner.dta;

import lombok.Data;

@Data
public class UrlRequest {

    private String originalUrl;

    private int validityDays;
}
