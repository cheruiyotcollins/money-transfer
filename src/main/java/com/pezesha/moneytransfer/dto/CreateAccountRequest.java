package com.pezesha.moneytransfer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pezesha.moneytransfer.model.Customer;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {
    private String customerName;
    private long nationalId;
    private String accountType;
    private String MSISDN;
    private String email;
    private double startingBalance;


}
