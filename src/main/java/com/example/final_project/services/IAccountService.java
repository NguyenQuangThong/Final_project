package com.example.final_project.services;

import com.example.final_project.dtos.requests.LoginRequest;
import com.example.final_project.dtos.requests.RegisterRequest;
import com.example.final_project.dtos.responses.LoginResponse;
import com.example.final_project.models.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public interface IAccountService extends UserDetailsService {
    LoginResponse login(LoginRequest loginRequest, Authentication authentication) throws NoSuchAlgorithmException, InvalidKeySpecException;
    boolean createAccount(RegisterRequest registerRequest);

    List<Account> getAllAccounts();
}
