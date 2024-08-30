package ntd.calculator.api.services;

import lombok.RequiredArgsConstructor;
import ntd.calculator.api.models.account.Account;
import ntd.calculator.api.models.user.User;
import ntd.calculator.api.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    private BigDecimal getBalanceForUser(User user) {
        return accountRepository.findByUser(user)
                .map(Account::getBalance)
                .orElseThrow(() -> new IllegalArgumentException("Account not found for user"));
    }

    public void checkSufficientBalanceByUser(User user, BigDecimal operationCost) {
        var balance = getBalanceForUser(user);
        if (balance.compareTo(operationCost) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
    }

}
