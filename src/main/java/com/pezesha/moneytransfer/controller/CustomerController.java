package com.pezesha.moneytransfer.controller;

import com.pezesha.moneytransfer.dto.ResponseDto;
import com.pezesha.moneytransfer.model.Customer;
import com.pezesha.moneytransfer.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value="/api/customers/")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    ResponseDto responseDto;
    @Operation(summary = "Listing All Customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "List Of All Customers",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Customer.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "No Customer not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @GetMapping("list")
    public ResponseEntity<?> findAll() throws ExecutionException, InterruptedException {
        CompletableFuture completableFuture=customerService.findAll();
        responseDto=(ResponseDto) completableFuture.get();
        return new ResponseEntity<>(responseDto,responseDto.getStatus());

    }
    @Operation(summary = "Find Customers By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Customer Found",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Customer.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Customer not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @GetMapping("find/{id}")
    public ResponseEntity<?> getAccountInfo(@PathVariable long id) throws ExecutionException, InterruptedException {
        CompletableFuture completableFuture=customerService.findById(id);
        responseDto=(ResponseDto) completableFuture.get();
        return new ResponseEntity<>(responseDto,responseDto.getStatus());

    }
}
