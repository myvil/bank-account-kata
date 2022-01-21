package com.griesba.kata.bankaccount.repository;

import com.griesba.kata.bankaccount.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OperationsRepository extends JpaRepository<AccountOperation, UUID> {
    List<AccountOperation> findBasedEventByIbanOrderByCreationDateDesc(String iban);
}
