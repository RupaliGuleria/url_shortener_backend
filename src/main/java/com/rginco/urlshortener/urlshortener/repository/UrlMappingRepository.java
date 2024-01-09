package com.rginco.urlshortener.urlshortener.repository;

import com.rginco.urlshortener.urlshortener.entity.URLMapping;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UrlMappingRepository extends CrudRepository<URLMapping,Long> {

    URLMapping findByLongUrl(String longUrl);
    URLMapping findByShortUrl(String shortUrl);

    // Custom query to retrieve shortUrl and hitCount
    @Query("SELECT u.shortUrl, u.hitCount FROM URLMapping u")
    List<Object[]> getHitCounts();

    // Custom query to update hitCount for a given shortUrl
    @Modifying
    @Transactional
    @Query("UPDATE URLMapping u SET u.hitCount = u.hitCount + 1 WHERE u.shortUrl = :shortUrl")
    void updateHitCount(@Param("shortUrl") String shortUrl);

}
