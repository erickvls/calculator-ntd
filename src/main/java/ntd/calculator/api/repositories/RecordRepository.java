package ntd.calculator.api.repositories;

import ntd.calculator.api.models.record.Record;
import ntd.calculator.api.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findRecordByUser(User user);
}
