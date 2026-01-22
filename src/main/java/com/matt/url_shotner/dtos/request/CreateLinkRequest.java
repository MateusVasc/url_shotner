package com.matt.url_shotner.dtos;

import java.util.UUID;

public record CreateLinkRequest(UUID userId, String longUrl) {
}
