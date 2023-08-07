package com.pezesha.moneytransfer.repository;

import com.pezesha.moneytransfer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "select * from customers c where c.national_id_no=:nationalIdNo",
            nativeQuery = true)
    Optional<Customer> findByNationalIdNo(@Param("nationalIdNo") long nationalIdNo);

    Optional<Customer> findByMsisdn(String msisdn);

    Optional<Customer> findByEmail(String email);
}
