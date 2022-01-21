package com.griesba.kata.bankaccount.web.model;

import com.griesba.kata.bankaccount.entities.OperationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDto {
    private  String iban;
    private double amount;
    private OperationType operationType;
    private OffsetDateTime creationDate;

    public OperationDto(String iban, double amount) {
        this.iban = iban;
        this.amount = amount;
    }
}
