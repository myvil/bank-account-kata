package com.griesba.bank.adapter;

import com.griesba.bank.domain.Account;
import com.griesba.bank.domain.repository.BankAccountRepository;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class BankAccountRepositoryImpl implements BankAccountRepository {

    Set<Account> bankAccounts = new LinkedHashSet<>();
    @Override
    public void save(Account account) {
        bankAccounts.add(account);
    }

    @Override
    public Set<Account> findAll() {
        return bankAccounts;
    }

    @Override
    public Optional<Account> findById(String id) {
        return bankAccounts.stream().filter(account -> account.getId().equals(id)).findFirst();
    }
}
