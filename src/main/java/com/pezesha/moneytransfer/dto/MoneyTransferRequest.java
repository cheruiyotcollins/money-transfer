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
    private String customerName;
    private long nationalId;
    private String accountType;
    private String MSISDN;
}
