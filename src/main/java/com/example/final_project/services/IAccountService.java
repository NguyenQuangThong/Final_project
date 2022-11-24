package com.example.final_project.services;

import com.example.final_project.dtos.requests.LoginRequest;
import com.example.final_project.dtos.requests.PasswordRequest;
import com.example.final_project.dtos.requests.RegisterRequest;
import com.example.final_project.dtos.responses.AccountResponse;
import com.example.final_project.dtos.responses.LoginResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public interface IAccountService extends UserDetailsService {
    LoginResponse login(LoginRequest loginRequest, Authentication authentication) throws NoSuchAlgorithmException, InvalidKeySpecException;

    boolean createAccount(RegisterRequest registerRequest);

    List<AccountResponse> getAllAccounts();

    AccountResponse getAccountById(Long id);

    boolean updateAccount(Long id, String fullName, MultipartFile avatar);
    boolean updateAccountPassword(Long id, PasswordRequest passwordRequest);

    boolean deleteAccount(Long id);
}
