package com.rginco.urlshortener.urlshortener.controller;

import com.rginco.urlshortener.urlshortener.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {


    private final UrlMappingRepository urlMappingRepository;

    // LinkedHashMap to store short URLs and their hit counts
    private final LinkedHashMap<String, Integer> mostUsedLinks = new LinkedHashMap<>();

    @Autowired
    public AnalyticsController(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    // Endpoint to retrieve top 100 most used links
    @GetMapping("/top100")
    public List<Map.Entry<String, Integer>> getTop100MostUsedLinks() {
        updateMostUsedLinks(); // Update the LinkedHashMap with the latest hit counts
        return mostUsedLinks.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(100)
                .collect(Collectors.toList());
    }

    // Helper method to update the hit counts in the LinkedHashMap
    private void updateMostUsedLinks() {
        // Retrieve hit counts from the database and update the LinkedHashMap
        List<Object[]> hitCounts = urlMappingRepository.getHitCounts();
        for (Object[] result : hitCounts) {
            String shortUrl = (String) result[0];
            int count = ((Number) result[1]).intValue();
            mostUsedLinks.put(shortUrl, count);
        }
    }

}
