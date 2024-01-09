package com.rginco.urlshortener.urlshortener.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlRequest {
    @NonNull
    private String longUrl;

}
