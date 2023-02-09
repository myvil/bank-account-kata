package config;

import com.griesba.bank.adapter.BankAccountRepositoryImpl;
import com.griesba.bank.adapter.TransactionRepositoryImpl;
import com.griesba.bank.domain.repository.BankAccountRepository;
import com.griesba.bank.domain.repository.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
public class SpringTestConfig {

    @Bean
    public BankAccountRepository bankAccountRepository() {
        return new BankAccountRepositoryImpl();
    }

    @Bean
    public TransactionRepository transactionRepository() {
        return new TransactionRepositoryImpl();
    }
}
