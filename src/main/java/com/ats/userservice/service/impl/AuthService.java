package com.ats.userservice.service.impl;

import com.ats.userservice.dto.AuthRequest;
import com.ats.userservice.dto.AuthResponse;
import com.ats.userservice.dto.UserResponse;
import com.ats.userservice.entity.RefreshToken;
import com.ats.userservice.entity.User;
import com.ats.userservice.exception.ResourceNotFoundException;
import com.ats.userservice.repository.RefreshTokenRepository;
import com.ats.userservice.repository.UserRepository;
//import com.ats.userservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

//@Service
//@RequiredArgsConstructor
//public class AuthService {
//
//    private final AuthenticationManager authManager;
//    private final JwtUtil jwtUtil;
//    private final UserRepository userRepository;
//    private final RefreshTokenRepository refreshTokenRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Value("${jwt.expirationMs}")
//    private Long jwtExpirationMs;
//
//    @Value("${jwt.refreshExpirationMs}")
//    private Long refreshExpirationMs;
//
//    /**
//     * Login method: generates access token and refresh token.
//     * Updates refresh token if already exists.
//     */
//    public AuthResponse login(AuthRequest request) {
//        // Authenticate user
//        authManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
//        );
//
//        User user = userRepository.findByUsername(request.getUsername())
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//        // Generate JWT access token
//        String accessToken = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
//
//        // Handle refresh token
//        RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
//                .map(existingToken -> {
//                    // Update existing refresh token
//                    existingToken.setToken(UUID.randomUUID().toString());
//                    existingToken.setCreatedAt(Instant.now());
//                    existingToken.setExpiresAt(Instant.now().plusMillis(refreshExpirationMs));
//                    return refreshTokenRepository.save(existingToken);
//                })
//                .orElseGet(() -> {
//                    // Create new refresh token
//                    RefreshToken newToken = RefreshToken.builder()
//                            .token(UUID.randomUUID().toString())
//                            .user(user)
//                            .expiresAt(Instant.now().plusMillis(refreshExpirationMs))
//                            .build();
//                    return refreshTokenRepository.save(newToken);
//                });
//
//        return AuthResponse.builder()
//                .token(accessToken)
//                .refreshToken(refreshToken.getToken())
//                .expiresIn(jwtExpirationMs / 1000) // in seconds
//                .user(mapToResponse(user))
//                .build();
//    }
//
//    /**
//     * Refresh access token using refresh token.
//     */
//    public AuthResponse refreshToken(String refreshTokenStr) {
//        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenStr)
//                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
//
//        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
//            // Delete expired token
//            refreshTokenRepository.delete(refreshToken);
//            throw new RuntimeException("Refresh token expired");
//        }
//
//        User user = refreshToken.getUser();
//        String accessToken = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
//
//        // Optionally: update refresh token for rolling token mechanism
//        refreshToken.setToken(UUID.randomUUID().toString());
//        refreshToken.setCreatedAt(Instant.now());
//        refreshToken.setExpiresAt(Instant.now().plusMillis(refreshExpirationMs));
//        refreshTokenRepository.save(refreshToken);
//
//        Long expiresInSeconds = Long.valueOf(jwtExpirationMs / 1000L);
//        return AuthResponse.builder()
//                .token(accessToken)
//                .expiresIn(expiresInSeconds)
//                .build();
//    }
//
//    /**
//     * Logout method: deletes refresh token for the user.
//     */
//    public void logout(String refreshToken) {
//        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
//                .orElseThrow(() -> new ResourceNotFoundException("Refresh token not found"));
//
//        refreshTokenRepository.delete(token);
//    }
//
//    /**
//     * Helper method to map User entity to UserResponse DTO.
//     */
//    private UserResponse mapToResponse(User user) {
//        return UserResponse.builder()
//                .userId(user.getUserId())
//                .username(user.getUsername())
//                .email(user.getEmail())
//                .name(user.getName())
//                .department(user.getDepartment())
//                .role(user.getRole().name())
//                .employeeId(user.getEmployeeId())
//                .build();
//    }
//}





//package com.ats.userservice.service.impl;

import com.ats.userservice.dto.AuthRequest;
import com.ats.userservice.dto.AuthResponse;
import com.ats.userservice.dto.UserResponse;
import com.ats.userservice.entity.RefreshToken;
import com.ats.userservice.entity.User;
import com.ats.userservice.exception.ResourceNotFoundException;
import com.ats.userservice.repository.RefreshTokenRepository;
import com.ats.userservice.repository.UserRepository;
import com.ats.userservice.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtProvider; // Use JwtTokenProvider
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.expirationMs}")
    private Long jwtExpirationMs;

    @Value("${jwt.refreshExpirationMs}")
    private Long refreshExpirationMs;

    /**
     * Login method: generates access token and refresh token.
     */
    public AuthResponse login(AuthRequest request) {
        // Authenticate user
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Generate JWT access token
        String accessToken = jwtProvider.generateToken(user);

        // Handle refresh token
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
                .map(existingToken -> {
                    existingToken.setToken(UUID.randomUUID().toString());
                    existingToken.setCreatedAt(Instant.now());
                    existingToken.setExpiresAt(Instant.now().plusMillis(refreshExpirationMs));
                    return refreshTokenRepository.save(existingToken);
                })
                .orElseGet(() -> {
                    RefreshToken newToken = RefreshToken.builder()
                            .token(UUID.randomUUID().toString())
                            .user(user)
                            .expiresAt(Instant.now().plusMillis(refreshExpirationMs))
                            .build();
                    return refreshTokenRepository.save(newToken);
                });

        return AuthResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken.getToken())
                .expiresIn(jwtExpirationMs / 1000) // in seconds
                .user(mapToResponse(user))
                .build();
    }

    /**
     * Refresh access token using refresh token.
     */
    public AuthResponse refreshToken(String refreshTokenStr) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenStr)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        User user = refreshToken.getUser();
        String accessToken = jwtProvider.generateToken(user);

        // Optional: rolling refresh token
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedAt(Instant.now());
        refreshToken.setExpiresAt(Instant.now().plusMillis(refreshExpirationMs));
        refreshTokenRepository.save(refreshToken);

        return AuthResponse.builder()
                .token(accessToken)
                .expiresIn(jwtExpirationMs / 1000)
                .build();
    }

    /**
     * Logout method: deletes refresh token for the user.
     */
    public void logout(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token not found"));

        refreshTokenRepository.delete(token);
    }

    /**
     * Helper method to map User entity to UserResponse DTO.
     */
    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .department(user.getDepartment())
                .role(user.getRole().name())
                .employeeId(user.getEmployeeId())
                .build();
    }
}

