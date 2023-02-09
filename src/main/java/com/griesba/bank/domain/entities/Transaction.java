package com.griesba.bank.domain.entities;

import java.math.BigDecimal;

public class Transaction {
    private final Operations operation;
    private final BigDecimal amount;
    private final String accountId;

    public Transaction(Operations operations, BigDecimal amount, String accountId) {
        if (amount.intValue() <= 0) { throw new UnsupportedOperationException("Provide positive amount");}
        this.operation = operations;
        this.amount = amount;
        this.accountId = accountId;
    }

    public Operations getOperation() {
        return operation;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getAccountId() {
        return accountId;
    }
}
