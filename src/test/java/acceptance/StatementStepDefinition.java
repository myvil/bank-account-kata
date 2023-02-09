package acceptance;

import com.griesba.bank.adapter.BankAccountRepositoryImpl;
import com.griesba.bank.adapter.TransactionRepositoryImpl;
import com.griesba.bank.domain.entities.Account;
import com.griesba.bank.domain.entities.AccountStatement;
import com.griesba.bank.domain.entities.Operations;
import com.griesba.bank.domain.entities.Transaction;
import com.griesba.bank.domain.repository.BankAccountRepository;
import com.griesba.bank.domain.repository.TransactionRepository;
import com.griesba.bank.usecases.BankTransaction;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class StatementStepDefinition {

    private Account account;
    private final BankAccountRepository accountRepository = new BankAccountRepositoryImpl();
    private final TransactionRepository transactionRepository = new TransactionRepositoryImpl();
    private List<AccountStatement> deposits;
    private List<AccountStatement> withdrawals;

    @Given("I am a client with id {string} and account number {string} and no money")
    public void iAmAClientWithIdAndAccountNumberAndNoMoney(String clientId, String accountId) {
        account = new Account(accountId, clientId, new BigDecimal(0));
        accountRepository.save(account);
    }

    @And("I have the following transactions to execute")
    public void iHaveTheFollowingTransactionsToExecute(DataTable dataTable) {
        List<List<String>> rows = dataTable.asLists(String.class);
        BankTransaction bankTransaction = new BankTransaction(accountRepository, transactionRepository);
        for (List<String> row: rows) {
            Transaction transaction = new Transaction(Operations.valueOf(row.get(0)),
                    BigDecimal.valueOf(Double.parseDouble(row.get(1))), account.getId());
            bankTransaction.execute(transaction);
        }

    }

    @When("I search for my account statement")
    public void iSearchForMyAccountStatement() {
        deposits = transactionRepository.findAll().stream().filter(stm -> stm.getOperation().equals(Operations.DEPOSIT)).collect(Collectors.toList());
        withdrawals = transactionRepository.findAll().stream().filter(stm -> stm.getOperation().equals(Operations.WITHDRAW)).collect(Collectors.toList());
    }

    @Then("I can find {int} deposit and {int} withdrawals")
    public void iCanFindDepositAndWithdrawals(int totalDeposit, int totalWithdrawal) {
        assertThat(totalDeposit, equalTo(deposits.size()));
        assertThat(totalWithdrawal, equalTo(withdrawals.size()));
    }


}
