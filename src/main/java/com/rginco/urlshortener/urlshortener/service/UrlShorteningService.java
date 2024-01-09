package com.rginco.urlshortener.urlshortener.service;

import com.rginco.urlshortener.urlshortener.entity.URLMapping;
import com.rginco.urlshortener.urlshortener.repository.UrlMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.zip.CRC32;

@Service
public class UrlShorteningService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlShorteningService.class);
    @Autowired
    UrlMappingRepository urlMappingRepository;

    public String generateShortUrl(String longUrl) {
        URLMapping existingMapping = urlMappingRepository.findByLongUrl(longUrl);

        if (existingMapping != null) {
            LOGGER.info("Short URL found in the database for long URL: {}", longUrl);
            return existingMapping.getShortUrl();
        } else {
            URLMapping newMapping = new URLMapping();
            newMapping.setLongUrl(longUrl);

            // Implement your own logic for generating a unique short URL
            String shortUrl = "http://tinyLink.in/" + generateUniqueShortKey(newMapping);
            newMapping.setShortUrl(shortUrl);
            newMapping.setHitCount(0); // Initialize hitCount to 0 intially

            LOGGER.info("New short URL generated for long URL: {}", longUrl);
            urlMappingRepository.save(newMapping);

            return shortUrl;
        }

    }


    private String generateUniqueShortKey(URLMapping newMapping) {

        try {
            // Long url link provided
            String longurl = newMapping.getLongUrl();

            // Calculate CRC32 hash
            String crc32ResultHex = calculateCRC32Hex(longurl);


            // Print the result
            System.out.println("Value of hash (hex): " + crc32ResultHex);

            // Take the first 7 characters as the short URL
            return crc32ResultHex;


        } catch (Exception e) {
            e.printStackTrace();
        }
        // This is just a placeholder, you may want to use a library for base62 conversion.
        return String.valueOf(System.currentTimeMillis());
    }

    private static String calculateCRC32Hex(String input) throws Exception {
        // Convert the input string to bytes
        byte[] bytes = input.getBytes("UTF-8");

        // Create a CRC32 object
        CRC32 crc32 = new CRC32();

        // Update the CRC32 object with the bytes
        crc32.update(bytes);
        LOGGER.info("Temporary Log- New short URL generated for long URL: {}", Long.toHexString(crc32.getValue()));
        // Get the CRC32 hash value in hexadecimal format
        return Long.toHexString(crc32.getValue());

    }

}
