package com.matt.url_shotner.controllers;

import com.matt.url_shotner.dtos.request.LoginRequest;
import com.matt.url_shotner.dtos.request.RefreshRequest;
import com.matt.url_shotner.dtos.response.LoginResponse;
import com.matt.url_shotner.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.authService.refresh(request.refreshToken()));
    }
}
