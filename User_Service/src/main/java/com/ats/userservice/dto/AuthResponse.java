package com.ats.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class AuthResponse {
    private String token;
    private String refreshToken;
    private long expiresIn;
    private UserResponse user;
}

