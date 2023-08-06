package com.pezesha.moneytransfer.service.customer;

import com.pezesha.moneytransfer.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;

public interface CustomerService {
    public CompletableFuture<ResponseDto> findById(long id);
    public CompletableFuture<ResponseDto> findAll();
}
