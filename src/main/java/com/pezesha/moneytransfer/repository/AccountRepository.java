package com.pezesha.moneytransfer.repository;

import com.pezesha.moneytransfer.model.Account;
import com.pezesha.moneytransfer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

}
