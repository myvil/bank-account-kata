package com.griesba.kata.bankaccount.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private UUID user;
    private  String iban;
    private double balance;

    public AccountDto(UUID user, String iban) {
        this.user = user;
        this.iban = iban;
    }
}
