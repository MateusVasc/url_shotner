package com.matt.url_shotner.controllers;

import com.matt.url_shotner.services.LinkService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LinkController {
    private final LinkService linkService;

    public ResponseEntity<String> getLongUrl(String shortUrl) {
        return ResponseEntity.status(HttpStatus.FOUND).body(linkService.findOriginalLongUrl(shortUrl));
    }

    public ResponseEntity<String> createShortUrl(String longUrl) {
        return ResponseEntity.status(HttpStatus.CREATED).body(linkService.createShortUrl(longUrl));
    }
}
