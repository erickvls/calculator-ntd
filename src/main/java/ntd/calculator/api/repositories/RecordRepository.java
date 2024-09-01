package ntd.calculator.api.repositories;

import ntd.calculator.api.models.record.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
}
