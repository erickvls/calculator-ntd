package ntd.calculator.api.repositories;

import ntd.calculator.api.models.record.Record;
import ntd.calculator.api.models.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    Page<Record> findRecordByUserAndDeletedIsFalse(User user, Pageable pageable);

    Optional<Record> findByIdAndUser(Long id, User user);
}
