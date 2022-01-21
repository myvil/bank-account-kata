package com.griesba.kata.bankaccount.repository;

import com.griesba.kata.bankaccount.entities.BankAccountCreated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankAccountCreatedRepository extends JpaRepository<BankAccountCreated, UUID> {
}
