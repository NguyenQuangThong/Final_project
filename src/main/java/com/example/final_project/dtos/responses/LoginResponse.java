package com.example.final_project.dtos.responses;

public class LoginResponse {
    String token;
    AccountResponse accountResponse;

    public LoginResponse(String token, AccountResponse accountResponse) {
        this.token = token;
        this.accountResponse = accountResponse;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AccountResponse getAccountResponse() {
        return accountResponse;
    }

    public void setAccountResponse(AccountResponse accountResponse) {
        this.accountResponse = accountResponse;
    }
}
