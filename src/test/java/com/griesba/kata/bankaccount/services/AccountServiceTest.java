package com.griesba.kata.bankaccount.services;

import com.griesba.kata.bankaccount.BankAccountException;
import com.griesba.kata.bankaccount.entities.AccountOperation;
import com.griesba.kata.bankaccount.entities.BankAccount;
import com.griesba.kata.bankaccount.entities.OperationType;
import com.griesba.kata.bankaccount.repository.BankAccountCreatedRepository;
import com.griesba.kata.bankaccount.repository.BankAccountRepository;
import com.griesba.kata.bankaccount.repository.OperationsRepository;
import com.griesba.kata.bankaccount.web.mappers.AccountEventMapper;
import com.griesba.kata.bankaccount.web.mappers.AccountMapper;
import com.griesba.kata.bankaccount.web.model.AccountDto;
import com.griesba.kata.bankaccount.web.model.OperationDto;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class AccountServiceTest {

    private final UUID USER = UUID.randomUUID();
    private final String IBAN = "FR55";

    @Mock BankAccountRepository bankAccountRepository;
    @Mock BankAccountCreatedRepository bankAccountCreatedRepository;
    @Mock
    OperationsRepository operationsRepository;
    AccountMapper accountMapper = new AccountMapper();;
    AccountEventMapper eventMapper = new AccountEventMapper();


    AccountService accountService;
    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        accountService = new AccountServiceImpl(bankAccountRepository, operationsRepository, bankAccountCreatedRepository, accountMapper, eventMapper);
    }

    @Test
    public void shouldSaveMoneyInClientAccount() throws BankAccountException {
        // given
        BankAccount bankAccount = new BankAccount(UUID.randomUUID(), USER, IBAN, 0.0);

        when(bankAccountRepository.findBankAccountByIban(any())).thenReturn(bankAccount);

        //when
        AccountDto deposit =  accountService.deposit(new OperationDto(IBAN, 500));

        //then
        Assertions.assertEquals(deposit.getBalance(), 500);

    }

    @Test
    public void shouldRetrieveMoneyInClientAccount() throws BankAccountException {
        // given
        BankAccount bankAccount = new BankAccount(UUID.randomUUID(), USER, IBAN, 500.0);

        when(bankAccountRepository.findBankAccountByIban(any())).thenReturn(bankAccount);

        //when
        AccountDto deposit =  accountService.withdraw(new OperationDto(IBAN, 400));

        //then
        Assertions.assertEquals(deposit.getBalance(), 100);

    }

    @Test
    public void shouldListAllClientOperation() {
        // given
        BankAccount bankAccount = new BankAccount(UUID.randomUUID(), USER, IBAN, 500.0);

        List<AccountOperation> eventDtoList = Arrays.asList(
                new AccountOperation(IBAN, 400, OperationType.DEPOSIT),
                new AccountOperation(IBAN, 500, OperationType.DEPOSIT),
                new AccountOperation(IBAN, 1000, OperationType.DEPOSIT),
                new AccountOperation(IBAN, 400, OperationType.WITHDRAW));

        when(bankAccountRepository.findBankAccountByIban(any())).thenReturn(bankAccount);

        when(operationsRepository.findBasedEventByIbanOrderByCreationDateDesc(any())).thenReturn(eventDtoList);

        // when
        List<OperationDto> accountHistory = accountService.getAccountHistory(IBAN);

        // then
        Assertions.assertEquals(accountHistory.size(), 4);

    }


    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }
}
