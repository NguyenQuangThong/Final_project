package com.example.final_project.controllers;

import com.example.final_project.dtos.requests.AccountUpdate;
import com.example.final_project.dtos.responses.AccountResponse;
import com.example.final_project.dtos.responses.MessageResponse;
import com.example.final_project.services.implement.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("")
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id) != null ? new ResponseEntity<>(accountService.getAccountById(id), HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateAccount(@PathVariable Long id, @RequestBody AccountUpdate accountUpdate) {
        if (accountService.updateAccount(id, accountUpdate))
            return new ResponseEntity<>(new MessageResponse("Account updated successfully!", HttpStatus.OK.value()), HttpStatus.OK);
        return new ResponseEntity<>(new MessageResponse("Account update error!", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteAccount(@PathVariable Long id) {
        if (accountService.deleteAccount(id))
            return new ResponseEntity<>(new MessageResponse("Account deleted successfully!", HttpStatus.OK.value()), HttpStatus.OK);
        return new ResponseEntity<>(new MessageResponse("Account delete error!", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
}
