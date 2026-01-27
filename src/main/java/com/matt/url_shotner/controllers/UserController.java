package com.matt.url_shotner.controllers;

import com.matt.url_shotner.dtos.request.CreateUserRequest;
import com.matt.url_shotner.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users/create")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request) {
        this.userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
