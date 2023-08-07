package com.pezesha.moneytransfer.service.transaction;

import com.pezesha.moneytransfer.dto.MoneyTransferRequest;
import com.pezesha.moneytransfer.dto.ResponseDto;

import java.util.concurrent.CompletableFuture;

public interface TransactionService {
    public CompletableFuture<ResponseDto> moneyTransfer(MoneyTransferRequest moneyTransferRequest);

    public CompletableFuture<ResponseDto> findAll();

    public CompletableFuture<ResponseDto> findById(long id);
}
