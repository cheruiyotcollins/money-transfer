package com.pezesha.moneytransfer.service;

import com.pezesha.moneytransfer.dto.CreateAccountRequest;
import com.pezesha.moneytransfer.dto.ResponseDto;
import com.pezesha.moneytransfer.model.Account;
import com.pezesha.moneytransfer.model.AccountType;
import com.pezesha.moneytransfer.model.Customer;
import com.pezesha.moneytransfer.repository.AccountRepository;
import com.pezesha.moneytransfer.repository.AccountTypeRepository;
import com.pezesha.moneytransfer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Slf4j
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AccountTypeRepository accountTypeRepository;
    ResponseDto responseDto;

    public ResponseEntity<?> createAccount(CreateAccountRequest createAccountRequest){
        responseDto=new ResponseDto();
         try{
             // Add new Customer if not found in customer table
             if (customerRepository.findByNationalIdNo(createAccountRequest.getNationalId()).isEmpty()) {
                 // call customerSetup method to create customer
                 customerSetup(createAccountRequest);

             }
             //create a new account by calling accountSetup method
              responseDto=  accountSetup(createAccountRequest);
         }catch (Exception e){
             responseDto.setStatus(HttpStatus.BAD_REQUEST);
             responseDto.setDescription(e.getMessage());

         }
        return new ResponseEntity<>(responseDto, responseDto.getStatus());

    }
    //setting up account from createAccountRequest
    private ResponseDto accountSetup(CreateAccountRequest createAccountRequest){
        responseDto=new ResponseDto();
        if(accountTypeRepository.findById(createAccountRequest.getAccountType()).isEmpty()){
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Provided Account Type Not Found");
            return responseDto;
        }
        if(createAccountRequest.getStartingBalance()<0){
            responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
            responseDto.setDescription("Starting Balance Cannot Be Less than 0");
            return responseDto;
        }
        Account account=new Account();
        account.setAccountBalance(createAccountRequest.getStartingBalance());
        account.setAccountName(createAccountRequest.getCustomerName());
        account.setAccountType(accountTypeRepository.findById(createAccountRequest.getAccountType()).get());
        account.setAccountStatus("ACTIVE");
        account.setCustomer(customerRepository.findByNationalIdNo(createAccountRequest.getNationalId()).get());
        account.setCreatedOn(LocalDateTime.now());
        accountRepository.save(account);
        responseDto.setPayload(account);
        responseDto.setStatus(HttpStatus.CREATED);
        responseDto.setDescription("Account has been created successfully");
        return responseDto;
    }

    private void customerSetup(CreateAccountRequest createAccountRequest){
        Customer customer=new Customer();
        customer.setCustomerName(createAccountRequest.getCustomerName());
        customer.setMsisdn(createAccountRequest.getMSISDN());
        customer.setEmail(createAccountRequest.getEmail());
        customer.setNationalIdNo(createAccountRequest.getNationalId());
        customer.setCreatedOn(LocalDateTime.now());
        customerRepository.save(customer);
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
    public ResponseEntity<?> createAccountType(AccountType accountType){
        responseDto=new ResponseDto();

        try{
            //check if there is no existing similar account type then creates a new one
            if(accountTypeRepository.findByAccountTypeName(accountType.getAccountTypeName()).isEmpty()){
               responseDto.setPayload(accountTypeRepository.save(accountType));
                responseDto.setStatus(HttpStatus.CREATED);
                responseDto.setDescription("Account Type Created Successfully");
                return new ResponseEntity<>(responseDto,responseDto.getStatus());
            }
            //if account Type already exists
            responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
            responseDto.setDescription("A similar account type name exists!!");
            return new ResponseEntity<>(responseDto,responseDto.getStatus());

        }catch (Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription(e.toString());
            return new ResponseEntity<>(responseDto,responseDto.getStatus());
        }


    }
}
