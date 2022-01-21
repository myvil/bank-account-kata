package com.griesba.kata.bankaccount.web.mappers;

import com.griesba.kata.bankaccount.entities.AccountOperation;
import com.griesba.kata.bankaccount.entities.OperationType;
import com.griesba.kata.bankaccount.web.model.OperationDto;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Component
public class AccountEventMapper {
    public OperationDto accountEventToDto(AccountOperation accountOperation) {
        OffsetDateTime creationDate = getOffsetDateTime(accountOperation.getCreationDate());
        return new OperationDto(accountOperation.getIban(), accountOperation.getAmount(), accountOperation.getOperationType(), creationDate);
    }

    public AccountOperation dtoToAccountWithdrawEvent(OperationDto operationDto) {
        return new AccountOperation(operationDto.getIban(), operationDto.getAmount(), OperationType.WITHDRAW);
    }

    public AccountOperation dtoToAccountDepositEvent(OperationDto operationDto) {
        return new AccountOperation(operationDto.getIban(), operationDto.getAmount(), OperationType.DEPOSIT);
    }

    private OffsetDateTime getOffsetDateTime(Timestamp timestamp) {
        if (timestamp == null) return null;
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getTime()), ZoneId.systemDefault());
        return offsetDateTime;
    }
}
