package com.ats.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.userservice.entity.RefreshToken;
import com.ats.userservice.entity.User;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	
	// Find refresh token by user
    Optional<RefreshToken> findByUser(User user);

    // Find refresh token by token string
    Optional<RefreshToken> findByToken(String token);

    // Delete refresh token by user
    void deleteByUser(User user);
}