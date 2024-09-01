package ntd.calculator.api.repositories;

import ntd.calculator.api.models.account.Account;
import ntd.calculator.api.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUser(User user);
}
