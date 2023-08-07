package com.pezesha.moneytransfer.service.customer;

import com.pezesha.moneytransfer.dto.CreateCustomerRequest;
import com.pezesha.moneytransfer.dto.ResponseDto;
import com.pezesha.moneytransfer.model.Customer;
import com.pezesha.moneytransfer.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    ResponseDto responseDto;

    public CompletableFuture<ResponseDto> findById(long id) {
        responseDto = new ResponseDto();
        if (customerRepository.findById(id).isPresent()) {
            Customer customer = customerRepository.findById(id).get();
            responseDto.setPayload(customer);
            responseDto.setStatus(HttpStatus.FOUND);
            responseDto.setDescription("Success");

        } else {
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Customer with provided id not found");
        }
        return CompletableFuture.completedFuture(responseDto);

    }

    public CompletableFuture<ResponseDto> findAll() {
        List<Customer> customers = customerRepository.findAll();
        responseDto = new ResponseDto();
        responseDto.setPayload(customers);
        responseDto.setStatus(HttpStatus.FOUND);
        responseDto.setDescription("List Of All Customers");
        return CompletableFuture.completedFuture(responseDto);
    }

    @Override
    @Async
    @Transactional
    public CompletableFuture<ResponseDto> addCustomer(CreateCustomerRequest createCustomerRequest) {
        //checks if there is an existing customer
        responseDto = new ResponseDto();
        if (customerRepository.findByEmail(createCustomerRequest.getEmail()).isPresent()) {
            responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
            responseDto.setDescription("A customer with similar email exists");
            return CompletableFuture.completedFuture(responseDto);

        }
        if (customerRepository.findByNationalIdNo(createCustomerRequest.getNationalId()).isPresent()) {
            responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
            responseDto.setDescription("A customer with similar national Id exists");
            return CompletableFuture.completedFuture(responseDto);

        }
        Customer customer = new Customer();
        customer.setCustomerName(createCustomerRequest.getCustomerName());
        customer.setMsisdn(createCustomerRequest.getMSISDN());
        customer.setEmail(createCustomerRequest.getEmail());
        customer.setNationalIdNo(createCustomerRequest.getNationalId());
        customer.setCreatedOn(LocalDateTime.now());
        customerRepository.save(customer);
        responseDto.setPayload(customer);
        responseDto.setDescription("Customer Added Successfully");
        responseDto.setStatus(HttpStatus.CREATED);
        return CompletableFuture.completedFuture(responseDto);


    }
}
