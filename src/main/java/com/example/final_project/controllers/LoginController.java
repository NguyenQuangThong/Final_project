package com.example.final_project.controllers;

import com.example.final_project.dtos.requests.LoginRequest;
import com.example.final_project.dtos.requests.RegisterRequest;
import com.example.final_project.dtos.responses.LoginResponse;
import com.example.final_project.dtos.responses.MessageResponse;
import com.example.final_project.repositories.AccountRepository;
import com.example.final_project.repositories.KeyRepository;
import com.example.final_project.services.implement.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
public class LoginController {
    @Autowired
    KeyRepository keyRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
        LoginResponse result = accountService.login(loginRequest, authentication);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody RegisterRequest registerRequest) {
        if (accountService.createAccount(registerRequest))
            return new ResponseEntity<>(new MessageResponse("Sign up successfully", HttpStatus.OK.value()), HttpStatus.OK);
        else
            return new ResponseEntity<>(new MessageResponse("Username already taken", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
}
