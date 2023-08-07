package com.pezesha.moneytransfer.service.account;

import com.pezesha.moneytransfer.dto.CreateAccountRequest;
import com.pezesha.moneytransfer.dto.ResponseDto;
import com.pezesha.moneytransfer.model.AccountType;

import java.util.concurrent.CompletableFuture;

public interface AccountService {
    public CompletableFuture<ResponseDto> createAccount(CreateAccountRequest createAccountRequest);

    public CompletableFuture<ResponseDto> findById(long id);

    public CompletableFuture<ResponseDto> findAll();

    public CompletableFuture<ResponseDto> createAccountType(AccountType accountType);

    CompletableFuture findAllAccountTypes();
}
