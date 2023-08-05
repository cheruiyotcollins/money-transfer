package com.pezesha.moneytransfer.controller;

import com.pezesha.moneytransfer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/customers/")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return customerService.findAll();

    }
    @GetMapping("find/{id}")
    public ResponseEntity<?> getAccountInfo(@PathVariable long id){
        return customerService.findById(id);
    }
}
