package com.pezesha.moneytransfer.controller;

import com.pezesha.moneytransfer.dto.CreateAccountRequest;
import com.pezesha.moneytransfer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/accounts/")
public class AccountController {
    @Autowired
    AccountService accountService;
    @PostMapping("new")
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest createAccountRequest){
        return accountService.createAccount(createAccountRequest);
    }
    @GetMapping("find/{id}")
    public ResponseEntity<?> getAccountInfo(@PathVariable long id){
        return accountService.findById(id);
    }
}
