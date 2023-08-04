package com.pezesha.moneytransfer.controller;

import com.pezesha.moneytransfer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;
}
