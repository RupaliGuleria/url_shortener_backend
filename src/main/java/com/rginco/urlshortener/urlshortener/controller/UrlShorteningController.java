package com.rginco.urlshortener.urlshortener.controller;

import com.rginco.urlshortener.urlshortener.model.UrlRequest;
import com.rginco.urlshortener.urlshortener.service.UrlShorteningService;
import com.rginco.urlshortener.urlshortener.entity.UrlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlShorteningController {
    @Autowired
    private UrlShorteningService urlShorteningService;

    @PostMapping("/shorten")
    public UrlResponse shortenUrl(@RequestBody UrlRequest longUrlRequest) {
        String shortUrl = urlShorteningService.generateShortUrl(longUrlRequest.getLongUrl());

        UrlResponse response = new UrlResponse();
        response.setShortUrl(shortUrl);

        return response;
    }
}
