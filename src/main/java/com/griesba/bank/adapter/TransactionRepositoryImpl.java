package com.griesba.bank.adapter;

import com.griesba.bank.domain.entities.AccountStatement;
import com.griesba.bank.domain.repository.TransactionRepository;

import java.util.LinkedHashSet;
import java.util.Set;


public class TransactionRepositoryImpl implements TransactionRepository {

    private Set<AccountStatement> accountStatements = new LinkedHashSet<>();

    @Override
    public Set<AccountStatement> findAll() {
        return accountStatements;
    }

    @Override
    public void save(AccountStatement accountStatement) {
        accountStatements.add(accountStatement);
    }

    @Override
    public Set<AccountStatement> findAllById(String accountId) {
        //Predicate<String> isAccountId = () ->
        //accountStatements.stream().filter(accountStatement -> accountStatement.getAccountId());
        throw new UnsupportedOperationException();
    }
}
