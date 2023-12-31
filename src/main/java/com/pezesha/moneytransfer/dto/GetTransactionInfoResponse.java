package com.pezesha.moneytransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetTransactionInfoResponse {
    private String transRef;
    private double transAmount;
    private long dbAccount;
    private long crAccount;
    private String description;
    private String transDate;
}



