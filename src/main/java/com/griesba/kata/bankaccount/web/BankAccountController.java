package com.griesba.kata.bankaccount.web;

import com.griesba.kata.bankaccount.BankAccountException;
import com.griesba.kata.bankaccount.services.AccountService;
import com.griesba.kata.bankaccount.web.model.AccountDto;
import com.griesba.kata.bankaccount.web.model.OperationDto;
import com.griesba.kata.bankaccount.web.model.OperationDtoList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/account")
@RestController
public class BankAccountController {

    private final AccountService accountService;

    public BankAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        try {
            return new ResponseEntity(accountService.createAccount(accountDto), HttpStatus.CREATED);
        } catch (BankAccountException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountDto> deposit(@RequestBody OperationDto operationDto) {
        try {
            return ResponseEntity.ok(accountService.deposit(operationDto));
        } catch (BankAccountException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountDto> withdraw(@RequestBody OperationDto operationDto) {
        try {
            return ResponseEntity.ok(accountService.withdraw(operationDto));
        } catch (BankAccountException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @GetMapping
    public List<AccountDto> listAccounts(){
        return accountService.listAccounts();
    }

    @GetMapping("/{iban}")
    public AccountDto getBankAccount(@PathVariable String iban){
        return accountService.getBankAccount(iban);
    }

    @GetMapping("/history/{iban}")
    public ResponseEntity<OperationDtoList> getHistory(@PathVariable  String iban) {
        return new ResponseEntity<>(new OperationDtoList(accountService.getAccountHistory(iban)), HttpStatus.OK);
    }
}
