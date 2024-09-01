package ntd.calculator.api.services;

import lombok.RequiredArgsConstructor;
import ntd.calculator.api.enums.OperationType;
import ntd.calculator.api.exceptions.OperationNotFoundException;
import ntd.calculator.api.models.operation.Operation;
import ntd.calculator.api.repositories.OperationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final OperationRepository operationRepository;
    public Operation findOperationByOperationType(OperationType operationType){
        return operationRepository.findByType(operationType)
                .orElseThrow(() -> new OperationNotFoundException(
                        String.format("%s:%s","Invalid operation type", operationType))
                );
    }
}
