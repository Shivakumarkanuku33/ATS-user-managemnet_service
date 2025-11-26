package com.ats.userservice.controller;

//import lombok.RequiredArgsConstructor;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import com.ats.userservice.dto.UserRequest;
//import com.ats.userservice.dto.UserResponse;
//import com.ats.userservice.security.JwtUtil;
//import com.ats.userservice.service.impl.UserService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/users")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserService userService;
// 
//    private final JwtUtil jwtUtil;
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping
//    public UserResponse createUser(@RequestBody UserRequest request) {
//        return userService.createUser(request);
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping
//    public List<UserResponse> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/{id}")
//    public UserResponse getUser(@PathVariable Long id) {
//        return userService.getUser(id);
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @PutMapping("/{id}")
//    public UserResponse updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
//        return userService.updateUser(id, request);
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/{id}")
//    public String deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return "User deleted successfully";
//    }
//
//    @GetMapping("/me")
//    public UserResponse getCurrentUser(@RequestHeader("Authorization") String authHeader) {
//
//        // remove Bearer prefix
//        String token = authHeader.replace("Bearer ", "");
//
//        // extract username from token
////        String username = jwtUtil.extractUsername(token);
//        String username = jwtUtil.getUsernameFromToken(token);
//
//        // fetch user details
//        return userService.getCurrentUser(username);
//    }
//}


//package com.ats.userservice.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ats.userservice.dto.UserRequest;
import com.ats.userservice.dto.UserResponse;
import com.ats.userservice.security.JwtTokenProvider;
import com.ats.userservice.service.impl.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtProvider; // updated

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest request) {
        return userService.createUser(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        return userService.updateUser(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

    // ===============================
    // Get current user based on token
    // ===============================
    @GetMapping("/me")
    public UserResponse getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        // Remove "Bearer " prefix
        String token = authHeader.replace("Bearer ", "");

        // Extract username from token
        String username = jwtProvider.getUsernameFromToken(token);

        // Fetch user details
        return userService.getCurrentUser(username);
    }
}
