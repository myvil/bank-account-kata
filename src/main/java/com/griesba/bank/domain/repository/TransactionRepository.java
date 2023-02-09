package com.griesba.bank.domain.repository;

import com.griesba.bank.domain.entities.AccountStatement;

import java.util.Set;

public interface TransactionRepository {
    Set<AccountStatement> findAll();

    Set<AccountStatement> findAllById(String accountId);

    void save(AccountStatement accountStatement);
}
