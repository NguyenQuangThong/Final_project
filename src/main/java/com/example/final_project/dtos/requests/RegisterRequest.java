package com.example.final_project.dtos.requests;

import lombok.Data;

@Data
public class RegisterRequest {
    String username;
    String password;
    String fullName;
    String email;
}
