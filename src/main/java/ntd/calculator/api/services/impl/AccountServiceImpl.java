package ntd.calculator.api.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ntd.calculator.api.exceptions.AccountNotFoundException;
import ntd.calculator.api.exceptions.InsufficientFundsException;
import ntd.calculator.api.models.account.Account;
import ntd.calculator.api.models.responses.AccountInfoResponse;
import ntd.calculator.api.models.user.User;
import ntd.calculator.api.repositories.AccountRepository;
import ntd.calculator.api.services.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public Account deductFunds(User user, BigDecimal amount) {
        var account = findAccountByUser(user);

        // Checks if user has enough funds for operation
        checkUserFundsForOperation(account, amount);

        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }

    @Override
    public void checkUserHasEnoughFunds(User user) {
        var account = findAccountByUser(user);
        var zero = BigDecimal.ZERO;
        if (account.getBalance().compareTo(zero) <= 0) {
            throw new InsufficientFundsException("Insufficient balance for this user");
        }
    }

    @Override
    public AccountInfoResponse findAccountInfo(User user) {
        var account = findAccountByUser(user);
        return AccountInfoResponse.builder()
                .email(user.getUsername())
                .balance(account.getBalance().toString())
                .build();
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
