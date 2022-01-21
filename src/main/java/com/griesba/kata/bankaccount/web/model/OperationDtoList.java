package com.griesba.kata.bankaccount.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationDtoList {
    private List<OperationDto> operations;
}
