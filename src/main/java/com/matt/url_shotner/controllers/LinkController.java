package com.matt.url_shotner.controllers;

import com.matt.url_shotner.dtos.request.CreateLinkRequest;
import com.matt.url_shotner.services.LinkService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@AllArgsConstructor
public class LinkController {
    private final LinkService linkService;

    @GetMapping("/links/{shortUrl}")
    public ResponseEntity<String> getLongUrl(@PathVariable String shortUrl) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(this.linkService.findOriginalLongUrl(shortUrl)))
                .build();
    }

    @PostMapping("/links")
    public ResponseEntity<String> createShortUrl(@RequestBody CreateLinkRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.linkService.createShortUrl(req.userId(), req.longUrl()));
    }
}
