package com.example.final_project.controllers;

import com.example.final_project.dtos.requests.PasswordRequest;
import com.example.final_project.dtos.responses.AccountResponse;
import com.example.final_project.dtos.responses.MessageResponse;
import com.example.final_project.services.implement.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping("/forgot-password")
    public ResponseEntity<Long> forgotPassword(@RequestParam String email) {
        Long result = accountService.sendCodeToMail(email);
        return result != null ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("")
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id) != null ? new ResponseEntity<>(accountService.getAccountById(id), HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<List<AccountResponse>> getAccountNotInClass(@PathVariable Long id) {
        return accountService.getMemberNotInClass(id) != null ? new ResponseEntity<>(accountService.getMemberNotInClass(id), HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/users")
    public ResponseEntity<List<AccountResponse>> getAllUser() {
        return accountService.getAllUserAccount() != null ? new ResponseEntity<>(accountService.getAllUserAccount(), HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateAccount(@PathVariable Long id, @RequestParam String fullName, @RequestParam String email, @RequestParam(required = false) MultipartFile avatar) {
        if (accountService.updateAccount(id, fullName, email, avatar))
            return new ResponseEntity<>(new MessageResponse("Account updated successfully!", HttpStatus.OK.value()), HttpStatus.OK);
        return new ResponseEntity<>(new MessageResponse("Account update error!", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<MessageResponse> updateAccountPassword(@PathVariable Long id, @RequestBody PasswordRequest passwordRequest) {
        if (accountService.updateAccountPassword(id, passwordRequest))
            return new ResponseEntity<>(new MessageResponse("Password updated successfully!", HttpStatus.OK.value()), HttpStatus.OK);
        return new ResponseEntity<>(new MessageResponse("Account update error!", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/reset/{id}")
    public ResponseEntity<Boolean> resetPassword(@PathVariable Long id, @RequestParam String password) {
        if (accountService.resetPassword(id, password)) return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteAccount(@PathVariable Long id) {
        if (accountService.deleteAccount(id))
            return new ResponseEntity<>(new MessageResponse("Account deleted successfully!", HttpStatus.OK.value()), HttpStatus.OK);
        return new ResponseEntity<>(new MessageResponse("Account delete error!", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
}
