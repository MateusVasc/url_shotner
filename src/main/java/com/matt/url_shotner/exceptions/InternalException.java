package com.matt.url_shotner.exceptions;

import com.matt.url_shotner.enums.ExceptionType;
import lombok.Getter;

@Getter
public class InternalException extends RuntimeException {
    private ExceptionType exceptionType;

    public InternalException(String message) {
        super(message);
    }

    public InternalException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }
}
