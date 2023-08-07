package com.pezesha.moneytransfer.controller;

import com.pezesha.moneytransfer.dto.CreateAccountRequest;
import com.pezesha.moneytransfer.dto.CreateAccountTypeRequest;
import com.pezesha.moneytransfer.dto.ResponseDto;
import com.pezesha.moneytransfer.model.Account;
import com.pezesha.moneytransfer.service.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api/accounts/")
@Slf4j
public class AccountController {
    @Autowired
    AccountService accountService;
    ResponseDto responseDto;

    @Operation(summary = "Create Customer Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account Created Successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = @Content),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    @PostMapping("new")
    public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) throws ExecutionException, InterruptedException {
        CompletableFuture completableFuture = accountService.createAccount(createAccountRequest);
        responseDto = (ResponseDto) completableFuture.get();
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }

    @Operation(summary = "Find Account By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Account Found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = @Content),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    @GetMapping("find/{id}")
    public ResponseEntity<?> getAccountInfo(@PathVariable long id) throws ExecutionException, InterruptedException {
        CompletableFuture completableFuture = accountService.findById(id);
        responseDto = (ResponseDto) completableFuture.get();
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }

    @Operation(summary = "Listing All Accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "List of All Accounts",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = @Content),
            @ApiResponse(responseCode = "404", description = "Accounts not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    @GetMapping("list")
    public ResponseEntity<?> findAll() throws ExecutionException, InterruptedException {
        CompletableFuture completableFuture = accountService.findAll();
        responseDto = (ResponseDto) completableFuture.get();
        return new ResponseEntity<>(responseDto, responseDto.getStatus());

    }

    @Operation(summary = "Creating Account Type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account Type Created Successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = @Content),
            @ApiResponse(responseCode = "404", description = "Account Type not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    @PostMapping("types/new")
    public ResponseEntity<?> createAccountType(@Valid @RequestBody CreateAccountTypeRequest accountType) throws ExecutionException, InterruptedException {
        CompletableFuture completableFuture = accountService.createAccountType(accountType);
        responseDto = (ResponseDto) completableFuture.get();

        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }

    @Operation(summary = "Listing All Account Types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "List of All Accounts Types",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized user", content = @Content),
            @ApiResponse(responseCode = "404", description = "Account Types not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    @GetMapping("types/list")
    public ResponseEntity<?> findAllAccountTypes() throws ExecutionException, InterruptedException {
        CompletableFuture completableFuture = accountService.findAllAccountTypes();
        responseDto = (ResponseDto) completableFuture.get();
        return new ResponseEntity<>(responseDto, responseDto.getStatus());

    }
}
