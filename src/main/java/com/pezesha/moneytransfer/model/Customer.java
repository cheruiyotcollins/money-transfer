package com.pezesha.moneytransfer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "msisdn"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        }),
        @UniqueConstraint(columnNames = {
                "nationalIdNo"
        })
})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerNo;
    @NotBlank
    private String customerName;
    @NotBlank

    private long nationalIdNo;
    @NotBlank
    private String msisdn;
    @Email
    private String email;
    @NotBlank
    private LocalDateTime createdOn;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> account;

}
