package com.matt.url_shotner.services;

import org.springframework.stereotype.Service;

@Service
public class Base62EncodingService {
    private static final String BASE62_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String base62EncodeUrl(int id) {
        StringBuilder shortUrl = new StringBuilder();

        while (id / 62 != 0) {
            int rem = id % 62;
            shortUrl.append(BASE62_ALPHABET.charAt(rem));
            id /= 62;
        }
        shortUrl.append(BASE62_ALPHABET.charAt(id));

        return shortUrl.reverse().toString();
    }
}
