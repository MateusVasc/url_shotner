package com.matt.url_shotner.repositories;

import com.matt.url_shotner.entities.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LinkRepository extends JpaRepository<Link, String> {
    @Query("SELECT l.originalUrl FROM Link l WHERE l.shortUrl = :")
    String findLongUrlByShortUrl(String shortUrl);
}
