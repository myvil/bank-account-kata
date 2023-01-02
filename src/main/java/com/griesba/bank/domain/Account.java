package com.griesba.bank.domain;


import java.math.BigDecimal;
import java.util.Objects;

import static com.griesba.bank.domain.Operations.DEPOSIT;
import static com.griesba.bank.domain.Operations.WITHDRAW;

public class Account {
    private final String userId;
    private final String id;
    private BigDecimal balance;

    public Account(String id, String userId, BigDecimal balance) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getId() {
        return id;
    }

    public Account updateBalance(Transaction transaction) {
        if (WITHDRAW == transaction.getOperation()) {
            this.balance = this.balance.subtract(transaction.getAmount());
        } else if (DEPOSIT == transaction.getOperation()) {
            this.balance = this.balance.add(transaction.getAmount());
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(userId, account.userId) && Objects.equals(id, account.id) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, id, balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "userId='" + userId + '\'' +
                ", id='" + id + '\'' +
                ", balance=" + balance +
                '}';
    }
}
