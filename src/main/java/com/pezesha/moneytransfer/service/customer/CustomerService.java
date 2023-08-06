package com.pezesha.moneytransfer.service.customer;

import org.springframework.http.ResponseEntity;

public interface CustomerService {
    public ResponseEntity<?> findById(long id);
    public ResponseEntity<?> findAll();
}
