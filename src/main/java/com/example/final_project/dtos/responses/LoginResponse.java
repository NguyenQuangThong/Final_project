package com.example.final_project.dtos.responses;

import lombok.Data;

@Data
public class LoginResponse {
    String token;
    AccountResponse accountResponse;

    public LoginResponse(String token, AccountResponse accountResponse) {
        this.token = token;
        this.accountResponse = accountResponse;
    }
}
