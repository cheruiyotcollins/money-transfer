package com.pezesha.moneytransfer.repository;

import com.pezesha.moneytransfer.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
    Optional<AccountType> findByAccountTypeName(String accountTypeName);

}
