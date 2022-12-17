package com.example.final_project.dtos.requests;

import lombok.Data;

@Data
public class PasswordRequest {
    String oldPassword;
    String newPassword;
}
