package com.pezesha.moneytransfer.service;

import com.pezesha.moneytransfer.dto.CreateAccountRequest;
import com.pezesha.moneytransfer.dto.ResponseDto;
import com.pezesha.moneytransfer.model.Account;
import com.pezesha.moneytransfer.repository.AccountRepository;
import com.pezesha.moneytransfer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.lang.Math;

@Service
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
        if(customerRepository.findById(createAccountRequest.getNationalId()).isPresent()){
            Account account=new Account();
            account.setAccountBalance(createAccountRequest.getStartingBalance());
            account.setAccountName(createAccountRequest.getCustomerName());
            account.setAccountType(createAccountRequest.getAccountType());
            account.setAccountStatus("ACTIVE");
            account.setCustomer(customerRepository.findByNationalIdNo(createAccountRequest.getNationalId()).get());
            accountRepository.save(account);
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setDescription("Your account has been created successfully, your account number is: "+account.getAccountNo()+", account name: "+account.getAccountName()+
                    ". Your current balance is: "+account.getAccountBalance());
        }



          return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }
}
