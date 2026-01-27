package com.matt.url_shotner.enums;

import org.springframework.http.HttpStatus;

public enum ExceptionType {
    LINK_NOT_FOUND_EXCEPTION("There is no short URL for this given link", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND_EXCEPTION("User not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS("An user with this e-mail already exists", HttpStatus.CONFLICT);

    public final String message;
    public final HttpStatus status;

    ExceptionType(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
