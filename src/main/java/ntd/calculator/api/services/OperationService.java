package ntd.calculator.api.services;

import ntd.calculator.api.enums.OperationType;
import ntd.calculator.api.models.operation.Operation;
import ntd.calculator.api.models.responses.OperationResponse;

import java.util.List;

public interface OperationService {
    Operation findOperationByOperationType(OperationType operationType);

    List<OperationResponse> findAll();
}
