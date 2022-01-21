package com.griesba.kata.bankaccount.repository;

import com.griesba.kata.bankaccount.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    BankAccount findBankAccountByIban(String iban);
}
