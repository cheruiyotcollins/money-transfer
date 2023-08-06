package com.pezesha.moneytransfer.service.transaction;

import com.pezesha.moneytransfer.dto.MoneyTransferRequest;
import org.springframework.http.ResponseEntity;

public interface TransactionService {
    public ResponseEntity<?> moneyTransfer(MoneyTransferRequest moneyTransferRequest);
    public ResponseEntity<?> findAll();
    public ResponseEntity<?> findById(long id);
}
