package com.matt.url_shotner.services;

import com.matt.url_shotner.entities.Link;
import com.matt.url_shotner.entities.User;
import com.matt.url_shotner.repositories.LinkRepository;
import com.matt.url_shotner.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LinkService {
    private final UserRepository userRepository;
    private final LinkRepository linkRepository;
    private final Base62EncodingService encodingService;

    public String findOriginalLongUrl(String shortUrl) {
        Link link = linkRepository.findById(shortUrl).orElseThrow(RuntimeException::new);
        return link.getOriginalUrl();
    }

    public String createShortUrl(UUID userId, String longUrl) {
        User owner = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        Link linkToSave = new Link();
        linkToSave.setOriginalUrl(longUrl);
        linkToSave.setOwner(owner);
        linkToSave.setCreatedAt(LocalDateTime.now());

        Link savedLink = linkRepository.save(linkToSave);

        String shortUrl = encodingService.base62EncodeUrl(savedLink.getId());
        savedLink.setShortUrl(shortUrl);

        linkRepository.save(savedLink);

        return shortUrl;
    }
}
