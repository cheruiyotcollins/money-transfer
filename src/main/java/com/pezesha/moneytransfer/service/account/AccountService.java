package com.pezesha.moneytransfer.service.account;

import com.pezesha.moneytransfer.dto.CreateAccountRequest;
import com.pezesha.moneytransfer.dto.ResponseDto;
import com.pezesha.moneytransfer.model.AccountType;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    public ResponseEntity<?> createAccount(CreateAccountRequest createAccountRequest);
    public ResponseEntity<?> findById(long id);
    public ResponseEntity<?> findAll();
    public ResponseEntity<?> createAccountType(AccountType accountType);
}
