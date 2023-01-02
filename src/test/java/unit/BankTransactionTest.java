package unit;

import com.griesba.bank.adapter.TransactionRepositoryImpl;
import com.griesba.bank.adapter.BankAccountRepositoryImpl;
import com.griesba.bank.domain.Account;
import com.griesba.bank.domain.Operations;
import com.griesba.bank.domain.Transaction;
import com.griesba.bank.domain.repository.TransactionRepository;
import com.griesba.bank.domain.repository.BankAccountRepository;
import com.griesba.bank.usecases.BankTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.equalTo;


public class BankTransactionTest {

    private final BankAccountRepository bankAccountRepository = new BankAccountRepositoryImpl();
    private final TransactionRepository transactionRepository = new TransactionRepositoryImpl();
    private final String customerId = "abc";

    @Nested
    @DisplayName("Deposit and Withdrawal")
    class DepositWithdrawal {
        @Test
        void shouldNotAcceptNegativeTransaction() {
            Exception thrown = Assertions.assertThrows(UnsupportedOperationException.class,
                    () -> new Transaction(Operations.DEPOSIT, new BigDecimal(-200), "123"));
            assertThat(thrown.getMessage(), equalTo("Provide positive amount"));
        }

        @Test
        void shouldNotConsiderUnknownAccount() {
            Account unknownAccount = new Account("aaa", customerId, new BigDecimal(100));

            Exception thrown = Assertions.assertThrows(NoSuchElementException.class,
                    () -> initTransaction(unknownAccount, Operations.DEPOSIT, new BigDecimal(1000)) );

            assertThat(thrown.getMessage(), equalTo("No account found with id : aaa"));
        }

        @Test
        void shouldSaveClientMoney() {
            Account accountPaul = initClientAccount(customerId, "123");
            initTransaction(accountPaul, Operations.DEPOSIT, new BigDecimal(1000));
            assertThat(bankAccountRepository.findAll(), hasItem(new Account(accountPaul.getId(), accountPaul.getUserId(), new BigDecimal(1000))));
        }


        @Test
        void shouldRetrievePartOfClientMoney() {
            Account accountPaul = initClientAccount(customerId, "123");
            initTransaction(accountPaul, Operations.DEPOSIT, new BigDecimal(1000));
            initTransaction(accountPaul, Operations.WITHDRAW, new BigDecimal(100));
            assertThat(bankAccountRepository.findAll(),
                    hasItem(new Account(accountPaul.getId(), accountPaul.getUserId(), new BigDecimal(900))));
        }
    }



    @Nested
    @DisplayName("Statement printing")
    class StatementPrinting {
        @Test
        void shouldListOperationHistory() {
            Account accountPaul = initClientAccount(customerId, "123");
            initTransaction(accountPaul, Operations.DEPOSIT, new BigDecimal(1000));
            initTransaction(accountPaul, Operations.WITHDRAW, new BigDecimal(100));
            initTransaction(accountPaul, Operations.WITHDRAW, new BigDecimal(600));

            assertThat(transactionRepository.findAll(), hasSize(3));

            long numberOfDeposit = transactionRepository.findAll().stream()
                    .filter(statement -> Operations.DEPOSIT == statement.getOperation()).count();
            assertThat(numberOfDeposit, equalTo(1L));

            long numberOfWithdraw = transactionRepository.findAll().stream()
                    .filter(statement -> Operations.WITHDRAW == statement.getOperation()).count();
            assertThat(numberOfWithdraw, equalTo(2L));

        }
    }

    private void initTransaction(Account accountPaul, Operations deposit, BigDecimal amount) {
        new BankTransaction(bankAccountRepository, transactionRepository)
                .execute(new Transaction(deposit, amount, accountPaul.getId()));
    }

    private Account initClientAccount(String customerId, String accountId) {
        Account accountPaul = new Account(accountId, customerId, new BigDecimal(0));
        bankAccountRepository.save(accountPaul);
        return accountPaul;
    }

}
