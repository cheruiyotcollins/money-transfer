package com.pezesha.moneytransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {
    private String name;
    private long stateIdentity;
    private long acType;
    private String phoneNo;
    private String customerEmail;
    private double initialBalance;




}
