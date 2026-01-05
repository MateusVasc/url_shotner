package com.matt.url_shotner.services;

import com.matt.url_shotner.entities.Link;
import com.matt.url_shotner.repositories.LinkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    public String findOriginalLongUrl(String shortUrl) {
        Link link = linkRepository.findById(shortUrl).orElseThrow(RuntimeException::new);
        return link.getOriginalUrl();
    }

    public String createShortUrl(String longUrl) {
        // encode the original url to shorten it
        // built link entity
        // save it

        return "on development";
    }
}
