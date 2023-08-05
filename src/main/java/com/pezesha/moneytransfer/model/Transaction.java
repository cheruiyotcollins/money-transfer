package com.pezesha.moneytransfer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "transactionRef"
        })
})
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    @NotBlank
    private long debitAccount;
    @NotBlank
    private long creditAccount;
    @NotBlank
    @Min(value=1)
    private double transactionAmount;
    @NotBlank
    private String transactionRef;
    private String transactionDetails;
    @NotBlank
    private String transactionDate;
}
