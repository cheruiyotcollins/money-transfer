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
    private long debitAccount;
    private long creditAccount;
    private long transactionAmount;
    private String transactionRef;
    private String transactionDetails;
    private String transactionDate;
}
