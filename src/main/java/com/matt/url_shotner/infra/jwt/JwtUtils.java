package com.matt.url_shotner.infra.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.matt.url_shotner.enums.ExceptionType;
import com.matt.url_shotner.exceptions.InternalException;
import com.matt.url_shotner.infra.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtUtils {
    @Value("${jwt.token.issuer}")
    private String issuer;

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.token.exp}")
    private Integer accessTokenExpirationTime;

    @Value("${jwt.refresh.exp}")
    private Integer refreshTokenExpirationTime;

    public String generateAccessToken(CustomUserDetails customUserDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(customUserDetails.getUsername())
                    .withExpiresAt(generateAccessTokenExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new InternalException(ExceptionType.FAILED_TO_CREATE_TOKEN);
        }
    }

    public String generateRefreshToken(CustomUserDetails customUserDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(customUserDetails.getUsername())
                    .withExpiresAt(generateRefreshTokenExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new InternalException(ExceptionType.FAILED_TO_CREATE_TOKEN);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new InternalException(ExceptionType.INVALID_TOKEN);
        }
    }

    public Instant generateAccessTokenExpirationDate() {
        return Instant.now().plusSeconds(accessTokenExpirationTime);
    }

    public Instant generateRefreshTokenExpirationDate() {
        return Instant.now().plusSeconds(refreshTokenExpirationTime);
    }

    public LocalDateTime getExpirationDateFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getExpiresAtAsInstant()
                    .atZone(ZoneOffset.UTC)
                    .toLocalDateTime();
        } catch (Exception e) {
            throw new InternalException(ExceptionType.FAILED_TO_RETRIEVE_EXPIRATION_TIME_FROM_TOKEN);
        }
    }
}
