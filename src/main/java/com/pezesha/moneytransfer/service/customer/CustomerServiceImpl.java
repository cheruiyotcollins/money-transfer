package com.pezesha.moneytransfer.service.customer;

import com.pezesha.moneytransfer.dto.ResponseDto;
import com.pezesha.moneytransfer.model.Customer;
import com.pezesha.moneytransfer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    CustomerRepository customerRepository;
    ResponseDto responseDto;

    public CompletableFuture<ResponseDto> findById(long id){
        responseDto=new ResponseDto();
        if(customerRepository.findById(id).isPresent()){
            Customer customer=  customerRepository.findById(id).get();
            responseDto.setPayload(customer);
            responseDto.setStatus(HttpStatus.FOUND);
            responseDto.setDescription("Success");

        }else {
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Customer with provided id not found");
        }
        return  CompletableFuture.completedFuture(responseDto);

    }
    public CompletableFuture<ResponseDto> findAll(){
        List<Customer> customers=customerRepository.findAll();
        responseDto=new ResponseDto();
        responseDto.setPayload(customers);
        responseDto.setStatus(HttpStatus.FOUND);
        responseDto.setDescription("List Of All Customers");
        return  CompletableFuture.completedFuture(responseDto);
    }
}
