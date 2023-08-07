package com.pezesha.moneytransfer.service.transaction;

import com.pezesha.moneytransfer.dto.MoneyTransferRequest;
import com.pezesha.moneytransfer.dto.ResponseDto;

import java.util.concurrent.CompletableFuture;

public interface TransactionService {
    CompletableFuture<ResponseDto> moneyTransfer(MoneyTransferRequest moneyTransferRequest);

    CompletableFuture<ResponseDto> findAll();

    CompletableFuture<ResponseDto> findById(long id);
}
