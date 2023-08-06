package com.pezesha.moneytransfer.controller;

import com.pezesha.moneytransfer.dto.MoneyTransferRequest;
import com.pezesha.moneytransfer.service.transaction.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/transactions/")
public class TransactionController {
    @Autowired
    TransactionServiceImpl transactionService;
    @PostMapping("new")
    public ResponseEntity<?> processTransaction(@RequestBody MoneyTransferRequest moneyTransferRequest){
      return  transactionService.moneyTransfer(moneyTransferRequest);
    }
    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return transactionService.findAll();

    }
    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return transactionService.findById(id);

    }
}
