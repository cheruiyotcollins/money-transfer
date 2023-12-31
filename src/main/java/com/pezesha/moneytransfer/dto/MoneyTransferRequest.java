package com.pezesha.moneytransfer.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransferRequest {
    private long dbAccount;
    private long crAccount;
    private long amount;
    private String transRef;
    private String description;
    private String transDate;
}
