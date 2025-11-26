package com.ats.userservice.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private String name;
    private String department;
    private String role;
    private Long employeeId;
}

