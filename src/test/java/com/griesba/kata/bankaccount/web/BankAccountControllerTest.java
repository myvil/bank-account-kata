package com.griesba.kata.bankaccount.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.griesba.kata.bankaccount.services.AccountService;
import com.griesba.kata.bankaccount.web.model.AccountDto;
import com.griesba.kata.bankaccount.web.model.OperationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankAccountController.class)
class BankAccountControllerTest {

    private final String BASE_URL = "/api/v1/account";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    @Autowired
    ObjectMapper objectMapper;

    AccountDto accountDto_1;
    AccountDto accountDto_2;

    @BeforeEach
    void setUp() {
        accountDto_1 = AccountDto.builder().iban("FR56").user(UUID.fromString("0777359f-cd8b-4dd1-8673-0693df8755ad")).build();
        accountDto_2 = AccountDto.builder().iban("FR56").balance(5000).user(UUID.fromString("0777359f-cd8b-4dd1-8673-0693df8755ad")).build();
    }

    @Test
    void createAccount() throws Exception {

        BDDMockito.given(accountService.createAccount(any())).willReturn(accountDto_1);

        MvcResult mvcResult = mockMvc.perform(
                post(BASE_URL + "/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(buildNewAccount())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.iban", equalTo("FR56")))
                .andReturn();
    }

    @Test
    void deposit() throws Exception {
        BDDMockito.given(accountService.deposit(any())).willReturn(accountDto_2);
        OperationDto depositEvent = OperationDto.builder().amount(5000).iban("FR56").build();
        mockMvc.perform(post(BASE_URL + "/deposit")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(depositEvent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance", equalTo(5000.0)))
                .andReturn();
    }

    @Test
    void withdraw() throws Exception {
        BDDMockito.given(accountService.withdraw(any())).willReturn(accountDto_2);
        OperationDto withdrawEvent = OperationDto.builder().amount(5000).iban("FR56").build();
        MvcResult result  = mockMvc.perform(post(BASE_URL + "/withdraw")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawEvent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance", equalTo(5000.0)))
                .andReturn();
    }

    @Test
    void getHistory() throws Exception {
        OperationDto operationDto1 = OperationDto.builder().amount(5000).iban("FR56").build();
        OperationDto operationDto2 = OperationDto.builder().amount(5000).iban("FR56").build();
        OperationDto operationDto3 = OperationDto.builder().amount(5000).iban("FR56").build();
        List<OperationDto> operations = Arrays.asList(operationDto1, operationDto2, operationDto3);
        BDDMockito.given(accountService.getAccountHistory(any())).willReturn(operations);
        mockMvc.perform(get(BASE_URL + "/history/FR56")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operations", hasSize(3)))
                .andReturn();
    }

    AccountDto buildNewAccount() {
        return  AccountDto.builder()
                .iban("FR56")
                .user(UUID.fromString("0777359f-cd8b-4dd1-8673-0693df8755ad"))
                .build();
    }
}