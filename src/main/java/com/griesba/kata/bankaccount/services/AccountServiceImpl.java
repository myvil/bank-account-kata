package com.griesba.kata.bankaccount.services;

import com.griesba.kata.bankaccount.BankAccountException;
import com.griesba.kata.bankaccount.entities.BankAccount;
import com.griesba.kata.bankaccount.entities.AccountOperation;
import com.griesba.kata.bankaccount.entities.BankAccountCreated;
import com.griesba.kata.bankaccount.entities.OperationType;
import com.griesba.kata.bankaccount.repository.BankAccountCreatedRepository;
import com.griesba.kata.bankaccount.repository.BankAccountRepository;
import com.griesba.kata.bankaccount.repository.OperationsRepository;
import com.griesba.kata.bankaccount.web.mappers.AccountEventMapper;
import com.griesba.kata.bankaccount.web.mappers.AccountMapper;
import com.griesba.kata.bankaccount.web.model.AccountDto;
import com.griesba.kata.bankaccount.web.model.OperationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final BankAccountRepository bankAccountRepository;
    private final OperationsRepository operationsRepository;
    private final BankAccountCreatedRepository bankAccountCreatedRepository;
    private final AccountMapper accountMapper;
    private final AccountEventMapper eventMapper;

    @Override
    public List<AccountDto> listAccounts() {
        return bankAccountRepository.findAll().stream().map(accountMapper::accountToDto).collect(Collectors.toList());
    }

    @Override
    public AccountDto getBankAccount(String iban) {
        return accountMapper.accountToDto(bankAccountRepository.findBankAccountByIban(iban));
    }

    @Override
    public List<OperationDto> getAccountHistory(String iban) {
        return operationsRepository.findBasedEventByIbanOrderByCreationDateDesc(iban)
                .stream().map(eventMapper::accountEventToDto).collect(Collectors.toList());
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) throws BankAccountException {
        BankAccount currenAccount = currentAccount(accountDto.getIban());
        if (currenAccount != null) {
            throw new BankAccountException("This account already exist");
        }
        bankAccountCreatedRepository.save(new BankAccountCreated(accountDto.getUser(), accountDto.getIban()));
        BankAccount bankAccount = bankAccountRepository.save(new BankAccount(null, accountDto.getUser(), accountDto.getIban(), accountDto.getBalance()));
        return accountMapper.accountToDto(bankAccount);
    }

    @Override
    public AccountDto deposit(OperationDto operationDto) throws BankAccountException {
        BankAccount currentAccount = currentAccount(operationDto.getIban());
        if (currentAccount == null) {
            throw new BankAccountException("Invalid account number");
        }
        double balance = currentAccount.getBalance() + operationDto.getAmount();
        BankAccount bankAccount = new BankAccount(currentAccount.getId(), currentAccount.getUser(), currentAccount.getIban(), balance);
        bankAccountRepository.save(bankAccount);
        operationsRepository.save(new AccountOperation(operationDto.getIban(), operationDto.getAmount(), OperationType.DEPOSIT));
        return accountMapper.accountToDto(bankAccount);
    }

    @Override
    public AccountDto withdraw(OperationDto operationDto) throws BankAccountException {
        BankAccount currentAccount = currentAccount(operationDto.getIban());
        if (currentAccount == null) {
            throw new BankAccountException("Invalid account number");
        }
        double balance = currentAccount.getBalance() - operationDto.getAmount();
        BankAccount bankAccount = new BankAccount(currentAccount.getId(), currentAccount.getUser(), currentAccount.getIban(), balance);
        bankAccountRepository.save(bankAccount);
        operationsRepository.save(new AccountOperation(operationDto.getIban(), operationDto.getAmount(), OperationType.WITHDRAW));
        return accountMapper.accountToDto(bankAccount);
    }

    private BankAccount currentAccount(String iban) {
        return bankAccountRepository.findBankAccountByIban(iban);
    }
}
