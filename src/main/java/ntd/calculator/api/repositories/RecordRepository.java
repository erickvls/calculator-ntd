package ntd.calculator.api.repositories;

import ntd.calculator.api.models.account.Account;
import ntd.calculator.api.models.record.Record;
import ntd.calculator.api.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findRecordByUser(User user);
}
