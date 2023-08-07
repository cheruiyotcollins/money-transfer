package com.pezesha.moneytransfer.service.customer;

import com.pezesha.moneytransfer.dto.CreateCustomerRequest;
import com.pezesha.moneytransfer.dto.GetCustomerResponse;
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
import java.util.ArrayList;
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

            GetCustomerResponse getCustomerResponse = setCustomerResponse(customer);

            responseDto.setPayload(getCustomerResponse);
            responseDto.setStatus(HttpStatus.FOUND);
            responseDto.setDescription("Success");
            log.info("::::::::::customer with provided id found");

        } else {
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Customer with provided stateIdentity not found");
        }
        return CompletableFuture.completedFuture(responseDto);

    }

    private GetCustomerResponse setCustomerResponse(Customer customer) {
        GetCustomerResponse getCustomerResponse = new GetCustomerResponse();
        getCustomerResponse.setCustomerEmail(customer.getEmail());
        getCustomerResponse.setName(customer.getCustomerName());
        getCustomerResponse.setPhoneNo(customer.getMsisdn());
        getCustomerResponse.setId(customer.getNationalIdNo());
        return getCustomerResponse;
    }

    public CompletableFuture<ResponseDto> findAll() {
        List<Customer> customers = customerRepository.findAll();
        List<GetCustomerResponse> getCustomerResponsesList = new ArrayList<>();
        customers.stream().forEach(customer -> {
                    GetCustomerResponse getCustomerResponse = setCustomerResponse(customer);
                    getCustomerResponsesList.add(getCustomerResponse);
                }
        );
        responseDto = new ResponseDto();
        responseDto.setPayload(getCustomerResponsesList);
        responseDto.setStatus(HttpStatus.FOUND);
        responseDto.setDescription("List Of All Customers");
        log.info("::::::::::list of all customers");
        return CompletableFuture.completedFuture(responseDto);
    }

    @Override
    @Async
    @Transactional
    public CompletableFuture<ResponseDto> addCustomer(CreateCustomerRequest createCustomerRequest) {
        //checks if there is an existing customer using email and national id
        responseDto = new ResponseDto();
        if (customerRepository.findByEmail(createCustomerRequest.getCustomerEmail()).isPresent()) {
            responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
            responseDto.setDescription("A customer with similar email exists");
            return CompletableFuture.completedFuture(responseDto);

        }
        if (customerRepository.findByNationalIdNo(createCustomerRequest.getId()).isPresent()) {
            responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
            responseDto.setDescription("A customer with similar national Id exists");
            return CompletableFuture.completedFuture(responseDto);

        }
        //creating new customer
        log.info("::::::::::adding new customer");
        Customer customer = new Customer();
        customer.setCustomerName(createCustomerRequest.getName());
        customer.setMsisdn(createCustomerRequest.getPhoneNo());
        customer.setEmail(createCustomerRequest.getCustomerEmail());
        customer.setNationalIdNo(createCustomerRequest.getId());
        customer.setCreatedOn(LocalDateTime.now());
        customerRepository.save(customer);
        responseDto.setDescription("Customer Added Successfully");
        responseDto.setStatus(HttpStatus.CREATED);
        return CompletableFuture.completedFuture(responseDto);


    }
}
