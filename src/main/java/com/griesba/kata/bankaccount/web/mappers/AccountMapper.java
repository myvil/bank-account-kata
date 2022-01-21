package com.griesba.kata.bankaccount.web.mappers;

import com.griesba.kata.bankaccount.entities.BankAccount;
import com.griesba.kata.bankaccount.web.model.AccountDto;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountDto accountToDto(BankAccount bankAccount) {
        return new AccountDto(bankAccount.getUser(), bankAccount.getIban(), bankAccount.getBalance());
    }

    public BankAccount dtoToBankAccount(AccountDto accountDto) {
        return new BankAccount(null, accountDto.getUser(), accountDto.getIban(), accountDto.getBalance());
    }
}
