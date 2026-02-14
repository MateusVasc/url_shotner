package com.matt.url_shotner.services;

import com.matt.url_shotner.dtos.request.LoginRequest;
import com.matt.url_shotner.dtos.response.LoginResponse;
import com.matt.url_shotner.entities.RefreshToken;
import com.matt.url_shotner.entities.User;
import com.matt.url_shotner.enums.ExceptionType;
import com.matt.url_shotner.exceptions.InternalException;
import com.matt.url_shotner.infra.jwt.JwtUtils;
import com.matt.url_shotner.infra.security.CustomUserDetails;
import com.matt.url_shotner.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    public LoginResponse login(LoginRequest request) {
        Authentication auth;

        Optional<User> user = userRepository.findByEmail(request.email());

        if (user.isEmpty()) throw new InternalException(ExceptionType.USER_NOT_FOUND_EXCEPTION);

        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        } catch (BadCredentialsException e) {
            throw new InternalException(ExceptionType.INCORRECT_CREDENTIALS);
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
        String accessToken = jwtUtils.generateAccessToken(customUserDetails);
        String refreshToken = jwtUtils.generateRefreshToken(customUserDetails);

        List<RefreshToken> nonRevokedTokens = refreshTokenService.getAllNonRevoked(user.get());
        if (!nonRevokedTokens.isEmpty()) refreshTokenService.revokeAllTokens(nonRevokedTokens);
        refreshTokenService.createToken(user.get(), refreshToken);

        return new LoginResponse(accessToken, refreshToken);
    }

    public LoginResponse refresh(String refreshToken) {
        return new LoginResponse("a", "a");
    }
}
