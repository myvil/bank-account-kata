package com.griesba.bank.domain.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class AccountStatement {
    private final Transaction transaction;
    private final Instant date;
    private final BigDecimal balance;

    public AccountStatement(Transaction transaction, Instant date, BigDecimal balance) {
        this.transaction = transaction;
        this.date = date;
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountStatement that = (AccountStatement) o;
        return Objects.equals(transaction, that.transaction) && Objects.equals(date, that.date) && Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transaction, date, balance);
    }

    @Override
    public String toString() {
        return "AccountStatement{" +
                "transaction=" + transaction +
                ", date=" + date +
                ", balance=" + balance +
                '}';
    }

    public Operations getOperation() {
        return transaction.getOperation();
    }
}
