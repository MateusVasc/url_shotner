package com.matt.url_shotner.repositories;

import com.matt.url_shotner.entities.RefreshToken;
import com.matt.url_shotner.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    @Query("SELECT r FROM RefreshToken r WHERE r.user = :user AND r.isRevoked IS FALSE")
    Optional<List<RefreshToken>> findByUserAndIsNotRevoked(User user);

    Optional<RefreshToken> findByToken(String token);
}
