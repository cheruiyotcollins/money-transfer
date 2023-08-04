package com.pezesha.moneytransfer.service;

import com.pezesha.moneytransfer.dto.CreateAccountRequest;
import com.pezesha.moneytransfer.dto.ResponseDto;
import com.pezesha.moneytransfer.model.Account;
import com.pezesha.moneytransfer.repository.AccountRepository;
import com.pezesha.moneytransfer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.lang.Math;

@Service
@Slf4j
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;
    ResponseDto responseDto;

    public ResponseEntity<?> createAccount(CreateAccountRequest createAccountRequest){
        responseDto=new ResponseDto();
        //checking if a customer exists with provided national id
        //if customer already exists, proceed to create account

        if(customerRepository.findByNationalIdNo(createAccountRequest.getNationalId()).isPresent()){
            Account account=new Account();
            account.setAccountBalance(createAccountRequest.getStartingBalance());
            account.setAccountName(createAccountRequest.getCustomerName());
            account.setAccountType(createAccountRequest.getAccountType());
            account.setAccountStatus("ACTIVE");
            account.setCustomer(customerRepository.findByNationalIdNo(createAccountRequest.getNationalId()).get());
            accountRepository.save(account);
            responseDto.setPayload(account);
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setDescription("Account has been created successfully");
            return new ResponseEntity<>(responseDto, responseDto.getStatus());
        }

        responseDto.setStatus(HttpStatus.NOT_FOUND);
        responseDto.setDescription("Customer with ..........doenst");
        return new ResponseEntity<>(responseDto, responseDto.getStatus());

    }
    public ResponseEntity<?> findById(long id){
        responseDto=new ResponseDto();
        if(accountRepository.findById(id).isPresent()){
          Account account=  accountRepository.findById(id).get();
          responseDto.setPayload(account);
          responseDto.setStatus(HttpStatus.FOUND);
          responseDto.setDescription("Account info retrieved successfully");

        }else {
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Account with provided id was not found");
        }
        return  new ResponseEntity<>(responseDto, responseDto.getStatus());

    }
}
