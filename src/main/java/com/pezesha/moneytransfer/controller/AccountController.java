package com.pezesha.moneytransfer.controller;

import com.pezesha.moneytransfer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;
}
