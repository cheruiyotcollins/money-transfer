package com.pezesha.moneytransfer.service.customer;

import com.pezesha.moneytransfer.dto.CreateCustomerRequest;
import com.pezesha.moneytransfer.dto.ResponseDto;

import java.util.concurrent.CompletableFuture;

public interface CustomerService {
    public CompletableFuture<ResponseDto> findById(long id);

    public CompletableFuture<ResponseDto> findAll();

    CompletableFuture addCustomer(CreateCustomerRequest createCustomerRequest);
}
