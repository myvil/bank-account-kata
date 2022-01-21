package com.griesba.kata.bankaccount.services;

import com.griesba.kata.bankaccount.BankAccountException;
import com.griesba.kata.bankaccount.web.model.AccountDto;
import com.griesba.kata.bankaccount.web.model.OperationDto;

import java.util.List;

public interface AccountService {

    List<AccountDto> listAccounts();

    AccountDto getBankAccount(String iban);

    List<OperationDto> getAccountHistory(String iban);

    AccountDto createAccount(AccountDto bankAccount) throws BankAccountException;

    AccountDto deposit(OperationDto operationDto) throws BankAccountException;

    AccountDto withdraw(OperationDto operationDto) throws BankAccountException;
}
