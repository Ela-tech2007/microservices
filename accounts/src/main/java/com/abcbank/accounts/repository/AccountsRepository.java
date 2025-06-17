package com.abcbank.accounts.repository;

import com.abcbank.accounts.entity.Accounts;
import com.abcbank.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Accounts,Long> {

    Accounts findByCustomerId(Long customerId);
}
