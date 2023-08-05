package com.pezesha.moneytransfer.service;

import com.pezesha.moneytransfer.dto.ResponseDto;
import com.pezesha.moneytransfer.model.Account;
import com.pezesha.moneytransfer.model.Customer;
import com.pezesha.moneytransfer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    ResponseDto responseDto;

    public ResponseEntity<?> findById(long id){
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
        return  new ResponseEntity<>(responseDto, responseDto.getStatus());

    }
    public ResponseEntity<?> findAll(){
        List<Customer> customers=customerRepository.findAll();
        responseDto=new ResponseDto();
        responseDto.setPayload(customers);
        responseDto.setStatus(HttpStatus.FOUND);
        responseDto.setDescription("List Of All Customers");
        return new ResponseEntity<>(responseDto,responseDto.getStatus());
    }
}
