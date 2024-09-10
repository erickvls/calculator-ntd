package ntd.calculator.api.services;

import ntd.calculator.api.enums.OperationType;
import ntd.calculator.api.exceptions.OperationNotFoundException;
import ntd.calculator.api.models.operation.Operation;
import ntd.calculator.api.repositories.OperationRepository;
import ntd.calculator.api.services.impl.OperationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OperationServiceTest {

    @Mock
    private OperationRepository operationRepository;

    @InjectMocks
    private OperationServiceImpl operationService;

    private Operation operation;
    private OperationType operationType;

    @BeforeEach
    void setUp() {
        operationType = OperationType.ADDITION;
        operation = new Operation();
        operation.setType(operationType);
    }

    @Test
    void shouldReturnOperationWhenOperationExists() {
        when(operationRepository.findByType(operationType)).thenReturn(Optional.of(operation));
        var foundOperation = operationService.findOperationByOperationType(operationType);
        assertEquals(operation, foundOperation);
    }

    @Test
    void shouldThrowOperationNotFoundExceptionWhenOperationDoesNotExist() {
        when(operationRepository.findByType(operationType)).thenReturn(Optional.empty());
        var exception = assertThrows(OperationNotFoundException.class, () -> {
            operationService.findOperationByOperationType(operationType);
        });
        assertEquals(String.format("Invalid operation type:%s", operationType), exception.getMessage());
    }

}