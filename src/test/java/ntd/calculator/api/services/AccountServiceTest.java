package ntd.calculator.api.services;

import ntd.calculator.api.exceptions.AccountNotFoundException;
import ntd.calculator.api.exceptions.InsufficientFundsException;
import ntd.calculator.api.models.account.Account;
import ntd.calculator.api.models.user.User;
import ntd.calculator.api.repositories.AccountRepository;
import ntd.calculator.api.services.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private User user;
    private Account account;

    @BeforeEach
    void setUp() {
        user = new User();
        account = new Account();
        account.setUser(user);
        account.setBalance(new BigDecimal("100.00"));
    }

    @Test
    void shouldDeductFundsWhenUserHasEnoughBalance() {
        var amount = new BigDecimal("50.00");
        when(accountRepository.findByUser(user)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));
        var updatedAccount = accountService.deductFunds(user, amount);
        assertEquals(new BigDecimal("50.00"), updatedAccount.getBalance());
        verify(accountRepository).save(updatedAccount);
    }

    @Test
    void shouldThrowInsufficientFundsExceptionWhenFundsIsLowerThanCost() {
        var amount = new BigDecimal("150.00");
        when(accountRepository.findByUser(user)).thenReturn(Optional.of(account));
        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class, () -> {
            accountService.deductFunds(user, amount);
        });
        assertEquals("Insufficient funds for this operation", exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }


    @Test
    void shouldNotThrowExceptionWhenUserHasBalance() {
        when(accountRepository.findByUser(user)).thenReturn(Optional.of(account));
        accountService.checkUserHasEnoughFunds(user);
        verify(accountRepository).findByUser(user);
    }

    @Test
    void checkUserHasEnoughFunds_shouldThrowInsufficientFundsException_whenBalanceIsZeroOrNegative() {
        account.setBalance(BigDecimal.ZERO);
        when(accountRepository.findByUser(user)).thenReturn(Optional.of(account));
        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class, () -> {
            accountService.checkUserHasEnoughFunds(user);
        });
        assertEquals("Insufficient balance for this user", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotExists() {
        when(accountRepository.findByUser(user)).thenReturn(Optional.empty());
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
            accountService.deductFunds(user, BigDecimal.TEN);
        });
        assertEquals("Account not found for this user", exception.getMessage());
    }
}
