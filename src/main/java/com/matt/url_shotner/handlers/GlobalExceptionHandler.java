package com.matt.url_shotner.handlers;

import com.matt.url_shotner.dtos.response.ExceptionResponse;
import com.matt.url_shotner.exceptions.InternalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<ExceptionResponse> handleInternalException(InternalException exception) {
        return ResponseEntity.status(exception.getExceptionType().status)
                .body(new ExceptionResponse(exception.getExceptionType().message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse("There was an unexpected issue with your request"));
    }
}
