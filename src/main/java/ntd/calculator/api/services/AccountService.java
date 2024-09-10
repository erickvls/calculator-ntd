package ntd.calculator.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ntd.calculator.api.exceptions.AccountNotFoundException;
import ntd.calculator.api.exceptions.InsufficientFundsException;
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

        // Checks if user has enough funds for operation
        checkUserFundsForOperation(account, amount);

        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }
    public void checkUserHasEnoughFunds(User user) {
        var account = findAccountByUser(user);
        var zero = BigDecimal.ZERO;
        if (account.getBalance().compareTo(zero) <= 0) {
            throw new InsufficientFundsException("Insufficient balance for this user");
        }
    }

    private Account findAccountByUser(User user) {
        return accountRepository.findByUser(user)
                .orElseThrow(() -> new AccountNotFoundException("Account not found for this user"));
    }

    private void checkUserFundsForOperation(Account account, BigDecimal operationCost) {
        if (account.getBalance().compareTo(operationCost) < 0) {
            throw new InsufficientFundsException("Insufficient funds for this operation");
        }
    }

}
