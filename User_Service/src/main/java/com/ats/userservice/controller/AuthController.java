package com.ats.userservice.controller;

import com.ats.userservice.dto.AuthRequest;
import com.ats.userservice.dto.AuthResponse;
import com.ats.userservice.service.impl.AuthService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    record RefreshReq(String refreshToken) {}

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshReq r) {
        return ResponseEntity.ok(authService.refreshToken(r.refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestBody RefreshReq r) {
        authService.logout(r.refreshToken);
        return ResponseEntity.noContent().build();
    }
}
