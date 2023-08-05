package com.pezesha.moneytransfer.service;

import com.pezesha.moneytransfer.dto.MoneyTransferRequest;
import com.pezesha.moneytransfer.dto.ResponseDto;
import com.pezesha.moneytransfer.model.Account;
import com.pezesha.moneytransfer.model.Transaction;
import com.pezesha.moneytransfer.repository.AccountRepository;
import com.pezesha.moneytransfer.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountRepository accountRepository;
    ResponseDto responseDto;

    public ResponseEntity<?> moneyTransfer(MoneyTransferRequest moneyTransferRequest){
        responseDto=new ResponseDto();
        try{
            //check if a similar transaction already exists
            if(transactionRepository.findByTransactionRef(moneyTransferRequest.getTransactionRef()).isPresent()){
                responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
                responseDto.setDescription("A Transaction With Similar Transaction Reference Already Exists  ");
                return new ResponseEntity<>(responseDto,responseDto.getStatus());
            }
           //check if transaction amount is less than 1
            double transactionAmount= moneyTransferRequest.getTransactionAmount();
            if(moneyTransferRequest.getTransactionAmount()<=0){
                responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
                responseDto.setDescription("Transaction Amount Must Be A Value More than 0");
                return new ResponseEntity<>(responseDto,responseDto.getStatus());
            }
            //checks if debit account exists
           if(accountRepository.findById(moneyTransferRequest.getDebitAccount()).isEmpty()){
               responseDto.setStatus(HttpStatus.NOT_FOUND);
               responseDto.setDescription("Debit Account Not Found");
               return new ResponseEntity<>(responseDto,responseDto.getStatus());
            }
            //checks if credit account exists
            if(accountRepository.findById(moneyTransferRequest.getDebitAccount()).isEmpty()){
                responseDto.setStatus(HttpStatus.NOT_FOUND);
                responseDto.setDescription("Credit Account Not Found");
                return new ResponseEntity<>(responseDto,responseDto.getStatus());
            }

            Account debitAccount=accountRepository.findById(moneyTransferRequest.getDebitAccount()).get();
            Account creditAccount=accountRepository.findById(moneyTransferRequest.getCreditAccount()).get();
            //checks if there is enough money in debit account for debiting
            if(debitAccount.getAccountBalance()-transactionAmount<0){
                responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
                responseDto.setDescription("Insufficient Funds In Debit Account to Complete This Transaction");
                return new ResponseEntity<>(responseDto,responseDto.getStatus());
            }
            //Debiting and Crediting respective accounts

            double debitAccountBalance=debitAccount.getAccountBalance()-transactionAmount;
            double creditAccountBalance=creditAccount.getAccountBalance()+transactionAmount;
            log.info("it reached here................."+debitAccountBalance);
            debitAccount.setAccountBalance(debitAccountBalance);
            creditAccount.setAccountBalance(creditAccountBalance);
            log.info("it reached here................."+debitAccount.getAccountBalance());
            accountRepository.save(debitAccount);
            accountRepository.save(creditAccount);

            //call processTransaction Method to set up and save transaction
            Transaction transaction= processTransaction(moneyTransferRequest);
            responseDto.setPayload(transaction);
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setDescription("Transaction Processed Successfully");
            return new ResponseEntity<>(responseDto,responseDto.getStatus());

        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription(e.getMessage());
            return new ResponseEntity<>(responseDto,responseDto.getStatus());
        }

    }
    //method for setting up and saving transaction
    private Transaction processTransaction(MoneyTransferRequest moneyTransferRequest){
        Transaction transaction= new Transaction();
        transaction.setTransactionAmount(moneyTransferRequest.getTransactionAmount());
        transaction.setTransactionDate(moneyTransferRequest.getTransactionDate());
        transaction.setTransactionRef(moneyTransferRequest.getTransactionRef());
        transaction.setCreditAccount(moneyTransferRequest.getCreditAccount());
        transaction.setDebitAccount(moneyTransferRequest.getDebitAccount());
        transaction.setTransactionDetails(moneyTransferRequest.getTransactionDetails());
        transactionRepository.save(transaction);
        return transaction;

    }
    public ResponseEntity<?> findAll(){

        List<Transaction> transactions=transactionRepository.findAll();
        responseDto=new ResponseDto();
        responseDto.setPayload(transactions);
        responseDto.setStatus(HttpStatus.FOUND);
        responseDto.setDescription("List Of All Transactions");
        return new ResponseEntity<>(responseDto,responseDto.getStatus());
    }

    public ResponseEntity<?> findById(long id){
        responseDto=new ResponseDto();
        if(transactionRepository.findById(id).isPresent()){
            Transaction transaction=  transactionRepository.findById(id).get();
            responseDto.setPayload(transaction);
            responseDto.setStatus(HttpStatus.FOUND);
            responseDto.setDescription("Transaction Details");

        }else {
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Transaction id not found");
        }
        return  new ResponseEntity<>(responseDto, responseDto.getStatus());

    }
}
