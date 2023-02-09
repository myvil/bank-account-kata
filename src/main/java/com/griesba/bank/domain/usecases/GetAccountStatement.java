package com.griesba.bank.domain.usecases;

import com.griesba.bank.domain.entities.AccountStatement;
import com.griesba.bank.domain.repository.BankAccountRepository;
import com.griesba.bank.domain.repository.TransactionRepository;

public class GetAccountStatement {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;

    public GetAccountStatement(BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
    }

/*    AccountStatement handle(String accountId) {
        return transactionRepository.findById(accountId)
                .map(account -> new AccountStatement())
                .orElseThrow(RuntimeException::new);

    }*/
}
