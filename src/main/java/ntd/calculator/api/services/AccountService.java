package ntd.calculator.api.services;

import ntd.calculator.api.models.account.Account;
import ntd.calculator.api.models.responses.AccountInfoResponse;
import ntd.calculator.api.models.user.User;

import java.math.BigDecimal;

public interface AccountService {
    Account deductFunds(User user, BigDecimal amount);
    void checkUserHasEnoughFunds(User user);
    AccountInfoResponse findAccountInfo(User user);
}
