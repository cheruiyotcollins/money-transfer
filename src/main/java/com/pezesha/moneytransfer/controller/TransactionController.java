package com.pezesha.moneytransfer.controller;

import com.pezesha.moneytransfer.dto.MoneyTransferRequest;
import com.pezesha.moneytransfer.dto.ResponseDto;
import com.pezesha.moneytransfer.model.Transaction;
import com.pezesha.moneytransfer.service.transaction.TransactionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api/transactions/")
@Slf4j
public class TransactionController {
    @Autowired
    TransactionServiceImpl transactionService;
    ResponseDto responseDto;

    @Operation(summary = "Account to Account Funds Transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction Uploaded Successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Transaction.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = @Content),
            @ApiResponse(responseCode = "404", description = "Transaction not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    @PostMapping("new")
    public ResponseEntity<?> processTransaction(@RequestBody MoneyTransferRequest moneyTransferRequest) throws ExecutionException, InterruptedException {

        CompletableFuture completableFuture = transactionService.moneyTransfer(moneyTransferRequest);
        responseDto = (ResponseDto) completableFuture.get();
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }

    @Operation(summary = "List All Transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction Uploaded Successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Transaction.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = @Content),
            @ApiResponse(responseCode = "404", description = "Transaction not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    @GetMapping("list")
    public ResponseEntity<?> findAll() throws ExecutionException, InterruptedException {

        CompletableFuture completableFuture = transactionService.findAll();
        responseDto = (ResponseDto) completableFuture.get();
        return new ResponseEntity<>(responseDto, responseDto.getStatus());

    }

    @Operation(summary = "Find transaction by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction retrieved",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Transaction.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = @Content),
            @ApiResponse(responseCode = "404", description = "Transaction not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) throws ExecutionException, InterruptedException {

        CompletableFuture completableFuture = transactionService.findById(id);
        responseDto = (ResponseDto) completableFuture.get();
        return new ResponseEntity<>(responseDto, responseDto.getStatus());


    }
}
