package com.griesba.kata.bankaccount.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import java.util.UUID;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankAccountCreated extends BaseEntity{

    private UUID user;

    private String iban;

    public BankAccountCreated(UUID id, UUID user, String iban) {
        super(id);
        this.user = user;
        this.iban = iban;
    }
}
