package acceptance;


import com.griesba.bank.domain.entities.Account;
import com.griesba.bank.domain.entities.Operations;
import com.griesba.bank.domain.entities.Transaction;
import com.griesba.bank.domain.repository.BankAccountRepository;
import com.griesba.bank.domain.repository.TransactionRepository;
import com.griesba.bank.usecases.BankTransaction;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TransactionStepDefinition {

    private Account account;
    private final BankAccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionStepDefinition(BankAccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Given("I am a client with id {string} and my account number is {string} with initial balance of {string} euro")
    public void iAmAClientWithIdAndMyAccountNumberIsWithInitialBalanceOfEuro(String clientId, String accountId, String balance) {
        account = new Account(accountId, clientId, BigDecimal.valueOf(Double.parseDouble(balance)));
        accountRepository.save(account);
    }

    @When("I make a deposit of {string} euros in my bank account")
    public void iMakeADepositOfEurosInMyBankAccount(String amount) {
        Transaction newTransaction = new Transaction(Operations.DEPOSIT, BigDecimal.valueOf(Double.parseDouble(amount)), account.getId());
        new BankTransaction(accountRepository, transactionRepository).execute(newTransaction);
    }

    @When("I make a withdrawal of {string} euros in my bank account")
    public void iMakeAWithdrawalOfEurosInMyBankAccount(String amount) {
        Transaction newTransaction = new Transaction(Operations.WITHDRAW, BigDecimal.valueOf(Double.parseDouble(amount)), account.getId());
        new BankTransaction(accountRepository, transactionRepository).execute(newTransaction);
    }

    @Then("the transaction is done and the final balance is {string}")
    public void theTransactionIsDoneAndTheFinalBalanceIs(String finalBalance) {
        assertThat(accountRepository.findById(account.getId()).get().getBalance(),
                equalTo(BigDecimal.valueOf(Double.parseDouble(finalBalance))));
    }


}
