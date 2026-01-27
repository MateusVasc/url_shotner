package com.matt.url_shotner.services;

import com.matt.url_shotner.entities.Link;
import com.matt.url_shotner.entities.User;
import com.matt.url_shotner.enums.ExceptionType;
import com.matt.url_shotner.exceptions.InternalException;
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
        Link link = this.linkRepository.findById(shortUrl).orElseThrow(() -> new InternalException(ExceptionType.LINK_NOT_FOUND_EXCEPTION));
        return link.getOriginalUrl();
    }

    public String createShortUrl(UUID userId, String longUrl) {
        Link linkToSave = new Link();

        if (userId != null) {
            User owner = this.userRepository.findById(userId).orElseThrow(() -> new InternalException(ExceptionType.USER_NOT_FOUND_EXCEPTION));
            linkToSave.setOwner(owner);
        }

        linkToSave.setOriginalUrl(longUrl);
        linkToSave.setCreatedAt(LocalDateTime.now());

        Link savedLink = this.linkRepository.save(linkToSave);

        String shortUrl = this.encodingService.base62EncodeUrl(savedLink.getId());
        savedLink.setShortUrl(shortUrl);

        this.linkRepository.save(savedLink);

        return shortUrl;
    }
}
