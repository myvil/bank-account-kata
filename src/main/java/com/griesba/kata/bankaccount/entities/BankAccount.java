package com.griesba.kata.bankaccount.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class BankAccount extends BaseEntity {

    private  UUID user;

    private  String iban;

    private double balance;

    public BankAccount(UUID id, UUID user, String iban, Double balance) {
        super(id);
        this.user = user;
        this.iban = iban;
        this.balance = balance;
    }

}
