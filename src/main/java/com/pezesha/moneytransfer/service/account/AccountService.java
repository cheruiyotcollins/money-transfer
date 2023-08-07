package com.pezesha.moneytransfer.service.account;

import com.pezesha.moneytransfer.dto.CreateAccountRequest;
import com.pezesha.moneytransfer.dto.CreateAccountTypeRequest;
import com.pezesha.moneytransfer.dto.ResponseDto;
import com.pezesha.moneytransfer.model.AccountType;

import java.util.concurrent.CompletableFuture;

public interface AccountService {
    CompletableFuture<ResponseDto> createAccount(CreateAccountRequest createAccountRequest);

    CompletableFuture<ResponseDto> findById(long id);

    CompletableFuture<ResponseDto> findAll();

    CompletableFuture<ResponseDto> createAccountType(CreateAccountTypeRequest accountType);

    CompletableFuture findAllAccountTypes();
}
