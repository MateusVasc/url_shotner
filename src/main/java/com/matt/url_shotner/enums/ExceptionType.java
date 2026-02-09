package com.matt.url_shotner.enums;

import org.springframework.http.HttpStatus;

public enum ExceptionType {
    LINK_NOT_FOUND_EXCEPTION("There is no short URL for this given link", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND_EXCEPTION("User not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS("An user with this e-mail already exists", HttpStatus.CONFLICT),
    INCORRECT_CREDENTIALS("Incorrect credentials", HttpStatus.UNAUTHORIZED),
    FAILED_TO_RETRIEVE_EXPIRATION_TIME_FROM_TOKEN("Failed to get expiration time from token", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_TOKEN("Invalid token", HttpStatus.INTERNAL_SERVER_ERROR),
    FAILED_TO_CREATE_TOKEN("Failed to create token", HttpStatus.INTERNAL_SERVER_ERROR);

    public final String message;
    public final HttpStatus status;

    ExceptionType(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
