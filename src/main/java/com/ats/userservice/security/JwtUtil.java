

package com.ats.userservice.security;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Base64;
//import java.util.Date;
//import java.util.function.Function;
//
//import javax.crypto.SecretKey;
//
//@Component
//public class JwtUtil {
//
//    private final SecretKey secretKey;
//
//    @Value("${jwt.expirationMs}")
//    private long jwtExpirationMs;
//
//    @Value("${jwt.refreshExpirationMs}")
//    private long jwtRefreshExpirationMs;
//
//    public JwtUtil(@Value("${jwt.secret}") String base64Secret) {
//        // Decode Base64 string into bytes for HS512
//        byte[] keyBytes = Base64.getDecoder().decode(base64Secret);
//        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    // Generate access token
//    public String generateToken(String username, String role) {
//        return Jwts.builder()
//                .setSubject(username)
//                .claim("role", role)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
//                .signWith(secretKey, SignatureAlgorithm.HS512)
//                .compact();
//    }
//
//    // Generate refresh token
//    public String generateRefreshToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshExpirationMs))
//                .signWith(secretKey, SignatureAlgorithm.HS512)
//                .compact();
//    }
//
//    // Extract username
//    public String getUsernameFromToken(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(secretKey)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    // Extract role
//    public String getRoleFromToken(String token) {
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(secretKey)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//        return (String) claims.get("role");
//    }
//
//    // Validate token
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            return false;
//        }
//    }
//    
//    // Extract username (new standard method)
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    // ⭐ ADD THIS — REQUIRED HELPER METHOD
//    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(secretKey)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//        return claimsResolver.apply(claims);
//    }
//}








//@Component
//public class JwtUtil {
//
//    private final SecretKey secretKey;
//
//    @Value("${jwt.expirationMs}")
//    private long jwtExpirationMs;
//
//    public JwtUtil(@Value("${jwt.secret}") String base64Secret) {
//        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Secret));
//    }
//
//    // Generate token with roles array and HS256
//    public String generateToken(String username, String role) {
//        return Jwts.builder()
//                .setSubject(username)
//                .claim("roles", new String[]{role}) // roles array
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
//                .signWith(secretKey, SignatureAlgorithm.HS256) // must match Asset Service
//                .compact();
//    }
//
//    public String getUsernameFromToken(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(secretKey)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            return false;
//        }
//    }
//}




