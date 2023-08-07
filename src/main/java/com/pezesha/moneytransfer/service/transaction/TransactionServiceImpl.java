package com.pezesha.moneytransfer.service.transaction;

import com.pezesha.moneytransfer.dto.MoneyTransferRequest;
import com.pezesha.moneytransfer.dto.ResponseDto;
import com.pezesha.moneytransfer.model.Account;
import com.pezesha.moneytransfer.model.Transaction;
import com.pezesha.moneytransfer.repository.AccountRepository;
import com.pezesha.moneytransfer.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class TransactionServiceImpl {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;
    ResponseDto responseDto;

    @Async
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public CompletableFuture<ResponseDto> moneyTransfer(MoneyTransferRequest moneyTransferRequest) {
        responseDto = new ResponseDto();
        try {
            //check if a similar transaction already exists
            if (transactionRepository.findByTransactionRef(moneyTransferRequest.getTransactionRef()).isPresent()) {
                responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
                responseDto.setDescription("A Transaction With Similar Transaction Reference Already Exists  ");
                return CompletableFuture.completedFuture(responseDto);
            }
            //check if transaction amount is less than 1
            double transactionAmount = moneyTransferRequest.getTransactionAmount();
            if (moneyTransferRequest.getTransactionAmount() <= 0) {
                responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
                responseDto.setDescription("Transaction Amount Must Be A Value More than 0");
                return CompletableFuture.completedFuture(responseDto);
            }
            //checks if debit account exists
            if (accountRepository.findById(moneyTransferRequest.getDebitAccount()).isEmpty()) {
                responseDto.setStatus(HttpStatus.NOT_FOUND);
                responseDto.setDescription("Debit Account Not Found");
                return CompletableFuture.completedFuture(responseDto);
            }
            //checks if credit account exists
            if (accountRepository.findById(moneyTransferRequest.getDebitAccount()).isEmpty()) {
                responseDto.setStatus(HttpStatus.NOT_FOUND);
                responseDto.setDescription("Credit Account Not Found");
                return CompletableFuture.completedFuture(responseDto);
            }

            Account debitAccount = accountRepository.findById(moneyTransferRequest.getDebitAccount()).get();
            Account creditAccount = accountRepository.findById(moneyTransferRequest.getCreditAccount()).get();
            //check if there is enough money in debit account for debiting
            if (debitAccount.getAccountBalance() - transactionAmount < 0) {
                responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
                responseDto.setDescription("Insufficient Funds In Debit Account to Complete This Transaction");
                return CompletableFuture.completedFuture(responseDto);
            }

            //Debiting and Crediting respective accounts
            double debitAccountBalance = debitAccount.getAccountBalance() - transactionAmount;
            double creditAccountBalance = creditAccount.getAccountBalance() + transactionAmount;
            debitAccount.setAccountBalance(debitAccountBalance);
            creditAccount.setAccountBalance(creditAccountBalance);
            accountRepository.save(debitAccount);
            accountRepository.save(creditAccount);

            //call processTransaction Method to set up and save transaction
            Transaction transaction = processTransaction(moneyTransferRequest);
            responseDto.setPayload(transaction);
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setDescription("Transaction Processed Successfully");
            return CompletableFuture.completedFuture(responseDto);

        } catch (Exception e) {
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription(e.getMessage());
            return CompletableFuture.completedFuture(responseDto);
        }

    }
    //method for setting up and saving transaction

    private Transaction processTransaction(MoneyTransferRequest moneyTransferRequest) {
        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(moneyTransferRequest.getTransactionAmount());
        transaction.setTransactionDate(moneyTransferRequest.getTransactionDate());
        transaction.setTransactionRef(moneyTransferRequest.getTransactionRef());
        transaction.setCreditAccount(moneyTransferRequest.getCreditAccount());
        transaction.setDebitAccount(moneyTransferRequest.getDebitAccount());
        transaction.setTransactionDetails(moneyTransferRequest.getTransactionDetails());
        transactionRepository.save(transaction);
        return transaction;

    }

    @Async
    public CompletableFuture<ResponseDto> findAll() {

        List<Transaction> transactions = transactionRepository.findAll();
        responseDto = new ResponseDto();
        responseDto.setPayload(transactions);
        responseDto.setStatus(HttpStatus.FOUND);
        responseDto.setDescription("List Of All Transactions");
        return CompletableFuture.completedFuture(responseDto);
    }

    @Async
    public CompletableFuture<ResponseDto> findById(long id) {
        responseDto = new ResponseDto();
        if (transactionRepository.findById(id).isPresent()) {
            Transaction transaction = transactionRepository.findById(id).get();
            responseDto.setPayload(transaction);
            responseDto.setStatus(HttpStatus.FOUND);
            responseDto.setDescription("Transaction Details");

        } else {
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Transaction id not found");
        }
        return CompletableFuture.completedFuture(responseDto);

    }
}
