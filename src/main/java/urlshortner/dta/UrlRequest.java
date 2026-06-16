package urlshortner.dta;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.URL;

@Data
public class UrlRequest {

    @NotBlank(message = "URL must not be blank")
    @URL(message = "Please provide a valid URL starting with http:// or https://")
    private String originalUrl;

    @Min(value = 0, message = "Validity days must be 0 or greater")
    private int validityDays;
}