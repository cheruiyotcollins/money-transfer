package com.pezesha.moneytransfer.controller;

import com.pezesha.moneytransfer.dto.ResponseDto;
import com.pezesha.moneytransfer.model.AccountType;
import com.pezesha.moneytransfer.repository.AccountTypeRepository;
import com.pezesha.moneytransfer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/accounts/types/")
public class AccountTypeController {
    @Autowired
    AccountService accountService;
    ResponseDto responseDto;
    @PostMapping("new")
    public ResponseEntity<?> createAccountType(@RequestBody AccountType accountType){
       return accountService.createAccountType(accountType);
    }
}
