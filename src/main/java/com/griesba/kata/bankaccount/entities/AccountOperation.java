package com.griesba.kata.bankaccount.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountOperation extends BaseEntity{

    protected String iban;

    protected double amount;

    @Enumerated(EnumType.STRING)
    protected OperationType operationType;

    @CreationTimestamp
    @Column(updatable = false)
    protected Timestamp creationDate;

    public AccountOperation(String iban, double amount, OperationType operationType) {
        this.iban = iban;
        this.amount = amount;
        this.operationType = operationType;
    }
}
