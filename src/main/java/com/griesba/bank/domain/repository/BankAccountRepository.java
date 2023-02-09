package com.griesba.bank.domain.repository;

import com.griesba.bank.domain.entities.Account;

import java.util.Optional;
import java.util.Set;

public interface BankAccountRepository {

    void save(Account account);

    Set<Account> findAll();

    Optional<Account> findById(String id);
}
