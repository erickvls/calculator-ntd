package ntd.calculator.api.repositories;

import ntd.calculator.api.enums.OperationType;
import ntd.calculator.api.models.operation.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    Optional<Operation> findByType(OperationType type);
}
