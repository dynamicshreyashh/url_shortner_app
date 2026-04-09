package com.example.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortenUrlRequest {

    @NotBlank(message = "url is required")
    private String url;

    @Size(min = 3, max = 50, message = "alias must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$", message = "alias can only contain letters, numbers, '-' or '_'")
    private String alias;
}
