package com.ats.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
    private String token;         // JWT access token
    private String refreshToken;  // Refresh token
    private long expiresIn;       // Expiration time in seconds
//    private Long userId;
    private String username;
    private String role;
    private UserResponse user;
}