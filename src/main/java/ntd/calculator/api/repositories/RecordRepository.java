package ntd.calculator.api.repositories;

import ntd.calculator.api.models.record.Record;
import ntd.calculator.api.models.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    Page<Record> findRecordByUser(User user, Pageable pageable);
}
