package ntd.calculator.api.services;

import jakarta.transaction.Transactional;
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

    @Transactional
    public Account deductFunds(User user, BigDecimal amount) {
        var account = findAccountByUser(user);

        // Checks if user has enough funds
        checkUserHasEnoughFunds(account);
        checkUserFundsForOperation(account, amount);

        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }

    private Account findAccountByUser(User user) {
        return accountRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Account not found for user: " + user.getUsername()));
    }

    private void checkUserFundsForOperation(Account account, BigDecimal operationCost) {
        if (account.getBalance().compareTo(operationCost) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

    private void checkUserHasEnoughFunds(Account account) {
        var zero = BigDecimal.ZERO;
        if (account.getBalance().compareTo(zero) <= 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
    }

}
