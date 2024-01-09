package com.rginco.urlshortener.urlshortener.controller;

import com.rginco.urlshortener.urlshortener.entity.URLMapping;
import com.rginco.urlshortener.urlshortener.repository.UrlMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class RedirectController {
    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @GetMapping("/{shortUrl}")
    public RedirectView redirect(@PathVariable String shortUrl) {

         final Logger LOGGER;
        LOGGER = LoggerFactory.getLogger(RedirectView.class);
        String redirectObj= "http://tinyLink.in/"+shortUrl;
        LOGGER.info("URl of short url to redirect is : "+redirectObj);
        URLMapping urlMapping = urlMappingRepository.findByShortUrl(redirectObj);


        if (urlMapping != null) {
            System.out.println("Value of urlMapping is :" +urlMapping.getLongUrl());
            //update the hit count first
            updateHitCount(redirectObj);

            // Redirect to the long URL
            return new RedirectView(urlMapping.getLongUrl());
        } else {
            //to handle exceptions - will do later
            LOGGER.error("Error while redirect");
            return new RedirectView("/error");
        }
    }

    //to be moved to servie layer - Update the hit count to track Frequency
    public void updateHitCount(String shortUrl) {
        urlMappingRepository.updateHitCount(shortUrl);
    }
}
