package com.matt.url_shotner.services;

import com.matt.url_shotner.entities.RefreshToken;
import com.matt.url_shotner.entities.User;
import com.matt.url_shotner.enums.ExceptionType;
import com.matt.url_shotner.exceptions.InternalException;
import com.matt.url_shotner.infra.jwt.JwtUtils;
import com.matt.url_shotner.repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtils jwtUtils;

    public List<RefreshToken> getAllNonRevoked(User user) {
        Optional<List<RefreshToken>> nonRevokedTokens = refreshTokenRepository.findByUserAndIsNotRevoked(user);
        return nonRevokedTokens.orElse(Collections.emptyList());
    }

    public void createToken(User owner, String refreshToken) {
        if (refreshTokenRepository.findByToken(refreshToken).isPresent()) throw new InternalException(ExceptionType.INVALID_TOKEN);

        RefreshToken refreshTokenToSave = new RefreshToken();
        refreshTokenToSave.setToken(refreshToken);
        refreshTokenToSave.setUser(owner);
        refreshTokenToSave.setCreatedAt(LocalDateTime.now());
        refreshTokenToSave.setExpiresAt(jwtUtils.getExpirationDateFromToken(refreshToken));
        refreshTokenToSave.setIsRevoked(Boolean.FALSE);

        refreshTokenRepository.save(refreshTokenToSave);
    }

    public void revokeAllTokens(List<RefreshToken> tokens) {
        for (RefreshToken t : tokens) {
            t.setIsRevoked(Boolean.TRUE);
        }
        refreshTokenRepository.saveAll(tokens);
    }
}
