package com.pezesha.moneytransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCustomerResponse {
    private String name;
    private long id;
    private String phoneNo;
    private String customerEmail;

}
