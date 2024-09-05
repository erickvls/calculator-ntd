package ntd.calculator.api.services;

import lombok.RequiredArgsConstructor;
import ntd.calculator.api.enums.OperationType;
import ntd.calculator.api.exceptions.OperationNotFoundException;
import ntd.calculator.api.models.operation.Operation;
import ntd.calculator.api.models.responses.OperationResponse;
import ntd.calculator.api.repositories.OperationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationService extends ResponseServiceBase{
    private final OperationRepository operationRepository;
    public Operation findOperationByOperationType(OperationType operationType){
        return operationRepository.findByType(operationType)
                .orElseThrow(() -> new OperationNotFoundException(
                        String.format("%s:%s","Invalid operation type", operationType))
                );
    }

    public List<OperationResponse> findAll(){
        var operations = operationRepository.findAll();
        return operations.stream().map(this::createOperationResponse).collect(Collectors.toList());
    }
}
