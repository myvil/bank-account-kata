package com.griesba.bank.usecases;

import com.griesba.bank.domain.Account;
import com.griesba.bank.domain.AccountStatement;
import com.griesba.bank.domain.Transaction;
import com.griesba.bank.domain.repository.TransactionRepository;
import com.griesba.bank.domain.repository.BankAccountRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.NoSuchElementException;

public class BankTransaction {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;

    public BankTransaction(BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void execute(Transaction transaction) {
        Account account = bankAccountRepository.findById(transaction.getAccountId())
                .orElseThrow(() -> new NoSuchElementException("No account found with id : " + transaction.getAccountId()))
                .updateBalance(transaction);
        saveTransaction(transaction, account.getBalance());
    }

    private void saveTransaction(Transaction transaction, BigDecimal balance) {
        transactionRepository.save(new AccountStatement(transaction, Instant.now(), balance));
    }
}
