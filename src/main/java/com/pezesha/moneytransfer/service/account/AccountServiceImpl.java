package com.pezesha.moneytransfer.service.account;

import com.pezesha.moneytransfer.dto.CreateAccountRequest;
import com.pezesha.moneytransfer.dto.CreateAccountTypeRequest;
import com.pezesha.moneytransfer.dto.GetAccountInfoResponse;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AccountTypeRepository accountTypeRepository;
    ResponseDto responseDto;

    @Async
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    //added Isolation.REPEATABLE_READ to provide pessimistic locking for concurrent requests handling and avoiding race conditions
    public CompletableFuture<ResponseDto> createAccount(CreateAccountRequest createAccountRequest) {
        responseDto = new ResponseDto();
        try {
            // Add new Customer if not found in customer table
            if (customerRepository.findByNationalIdNo(createAccountRequest.getStateIdentity()).isEmpty()) {
                // call customerSetup method to create customer
                customerSetup(createAccountRequest);

            }
            //create a new account by calling accountSetup method
            responseDto = accountSetup(createAccountRequest);
        } catch (Exception e) {
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription(e.getMessage());

        }
        return CompletableFuture.completedFuture(responseDto);

    }

    //setting up account from createAccountRequest
    private ResponseDto accountSetup(CreateAccountRequest createAccountRequest) {
        responseDto = new ResponseDto();
        if (accountTypeRepository.findById(createAccountRequest.getAcType()).isEmpty()) {
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Provided Account Type Not Found");
            return responseDto;
        }
        if (createAccountRequest.getInitialBalance() < 0) {
            responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
            responseDto.setDescription("Starting Balance Cannot Be Less than 0");
            return responseDto;
        }
        Account account = new Account();
        account.setAccountBalance(createAccountRequest.getInitialBalance());
        account.setAccountName(createAccountRequest.getName());
        account.setAccountType(accountTypeRepository.findById(createAccountRequest.getAcType()).get());
        account.setAccountStatus("ACTIVE");
        account.setCustomer(customerRepository.findByNationalIdNo(createAccountRequest.getStateIdentity()).get());
        account.setCreatedOn(LocalDateTime.now());
        accountRepository.save(account);
        responseDto.setStatus(HttpStatus.CREATED);
        responseDto.setDescription("Account has been created successfully");
        log.info("::::::::::account created successfully");
        return responseDto;
    }

     //used for creating a new customer when create account request doesn't find existing customer
    private void customerSetup(CreateAccountRequest createAccountRequest) {
        Customer customer = new Customer();
        customer.setCustomerName(createAccountRequest.getName());
        customer.setMsisdn(createAccountRequest.getPhoneNo());
        customer.setEmail(createAccountRequest.getCustomerEmail());
        customer.setNationalIdNo(createAccountRequest.getStateIdentity());
        customer.setCreatedOn(LocalDateTime.now());
        log.info("::::::::::adding new customer");
        customerRepository.save(customer);
    }


    public CompletableFuture<ResponseDto> findById(long id) {
        responseDto = new ResponseDto();
        if (accountRepository.findById(id).isPresent()) {
            Account account = accountRepository.findById(id).get();
           GetAccountInfoResponse getAccountInfoResponse= setAccountInfoResponse(account);
            responseDto.setPayload(getAccountInfoResponse);
            responseDto.setStatus(HttpStatus.FOUND);
            responseDto.setDescription("Account info retrieved successfully");
            log.info("::::::::::account info retrieved successfully");

        } else {
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Account with provided id was not found");
        }
        return CompletableFuture.completedFuture(responseDto);

    }


    public CompletableFuture<ResponseDto> findAll() {
        List<Account> accounts = accountRepository.findAll();
        List<GetAccountInfoResponse> getAccountInfoResponseList=new ArrayList<>();
        accounts.stream().forEach(account -> {
            GetAccountInfoResponse getAccountInfoResponse=setAccountInfoResponse(account);
            getAccountInfoResponseList.add(getAccountInfoResponse);
        });
        responseDto = new ResponseDto();
        responseDto.setPayload(getAccountInfoResponseList);
        responseDto.setStatus(HttpStatus.FOUND);
        responseDto.setDescription("List Of All Accounts");
        log.info("::::::::::list of all accounts");
        return CompletableFuture.completedFuture(responseDto);
    }

    private GetAccountInfoResponse setAccountInfoResponse(Account account){
        GetAccountInfoResponse getAccountInfoResponse=new GetAccountInfoResponse();
        getAccountInfoResponse.setAcName(account.getAccountName());
        getAccountInfoResponse.setAcBalance(account.getAccountBalance());
        getAccountInfoResponse.setAcNumber(account.getAccountNo());
        getAccountInfoResponse.setAcType(account.getAccountType().getAccountTypeName());
        return getAccountInfoResponse;

    }

    @Async
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public CompletableFuture<ResponseDto> createAccountType(CreateAccountTypeRequest accountType) {
        responseDto = new ResponseDto();

        try {
            //check if there is no existing similar account type then creates a new one
            if (accountTypeRepository.findByAccountTypeName(accountType.getAcTypeName()).isEmpty()) {
                AccountType acType= new AccountType();
                acType.setAccountTypeName(accountType.getAcTypeName());
                responseDto.setPayload(accountTypeRepository.save(acType));
                responseDto.setStatus(HttpStatus.CREATED);
                responseDto.setDescription("Account Type Created Successfully");
                log.info("::::::::::created new account type. Name= "+acType.getAccountTypeName());
                return CompletableFuture.completedFuture(responseDto);
            }
            //if account Type already exists
            responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
            responseDto.setDescription("A similar account type roleName exists!!");
            return CompletableFuture.completedFuture(responseDto);

        } catch (Exception e) {
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription(e.toString());
            return CompletableFuture.completedFuture(responseDto);
        }


    }

    @Override
    @Async
    public CompletableFuture<ResponseDto> findAllAccountTypes() {
        responseDto = new ResponseDto();
        try {
            responseDto.setPayload(accountTypeRepository.findAll());
            responseDto.setStatus(HttpStatus.FOUND);
            responseDto.setDescription("List of all account types");
        } catch (Exception e) {
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription(e.getMessage());
        }
        return CompletableFuture.completedFuture(responseDto);

    }

}
