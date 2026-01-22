package com.matt.url_shotner.enums;

import org.springframework.http.HttpStatus;

public enum ExceptionType {
    LINK_NOT_FOUND_EXCEPTION("There is no short URL for this given link", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND_EXCEPTION("User not found", HttpStatus.NOT_FOUND);

    public final String message;
    public final HttpStatus status;

    ExceptionType(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
