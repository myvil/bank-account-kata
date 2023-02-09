package com.griesba.bank.domain.usecases;

import com.griesba.bank.domain.entities.Account;
import com.griesba.bank.domain.entities.Transaction;
import com.griesba.bank.domain.repository.BankAccountRepository;

import java.util.NoSuchElementException;

public class MakeDeposit {


    private BankAccountRepository bankAccountRepository;

    public void execute(Transaction transaction) {
        Account account = bankAccountRepository.findById(transaction.getAccountId())
                .orElseThrow(() -> new NoSuchElementException("No account found with id : " + transaction.getAccountId()))
                .updateBalance(transaction);
        //bankAccountRepository.saveTransaction(transaction, account.getBalance());
    }
}
