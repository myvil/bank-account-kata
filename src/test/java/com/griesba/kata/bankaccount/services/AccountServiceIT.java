package com.griesba.kata.bankaccount.services;

import com.griesba.kata.bankaccount.BankAccountException;
import com.griesba.kata.bankaccount.web.model.AccountDto;
import com.griesba.kata.bankaccount.web.model.OperationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class AccountServiceIT {

    @Autowired
    AccountService accountService;

    @Test
    void shouldCreateAccount() throws BankAccountException {
        AccountDto accountCreated = accountService.createAccount(new AccountDto(UUID.randomUUID(), "FR53"));
        Assertions.assertEquals("FR53", accountCreated.getIban());
    }

    @Test
    void shouldSaveClientMoney() throws BankAccountException {
        AccountDto accountBefore = accountService.createAccount(new AccountDto(UUID.randomUUID(), "FR54"));
        AccountDto accountAfter= accountService.deposit(new OperationDto(accountBefore.getIban(), 500));
        Assertions.assertEquals(accountAfter.getBalance(), 500.0);
    }

    @Test
    void shouldWithdrawClientMoney() throws BankAccountException {
        AccountDto accountBefore = accountService.createAccount(new AccountDto(UUID.randomUUID(), "FR55"));
        accountService.deposit(new OperationDto(accountBefore.getIban(), 500));
        AccountDto accountAfter= accountService.withdraw(new OperationDto(accountBefore.getIban(), 500));
        Assertions.assertEquals(accountAfter.getBalance(), 0.0);
    }

    @Test
    void shouldListAccountHistory() throws BankAccountException {
        accountService.createAccount(new AccountDto( UUID.randomUUID(), "FR56"));
        accountService.deposit(new OperationDto("FR56", 500));
        accountService.deposit(new OperationDto("FR56", 500));
        accountService.withdraw(new OperationDto("FR56", 300));
        List<OperationDto> history = accountService.getAccountHistory("FR56");
        Assertions.assertEquals(history.size(), 3);
    }

}