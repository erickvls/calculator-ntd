package ntd.calculator.api.services;

import ntd.calculator.api.enums.OperationType;
import ntd.calculator.api.exceptions.InsufficientFundsException;
import ntd.calculator.api.models.account.Account;
import ntd.calculator.api.models.operation.Operation;
import ntd.calculator.api.models.record.Record;
import ntd.calculator.api.models.requests.CalculationRequest;
import ntd.calculator.api.models.user.User;
import ntd.calculator.api.strategy.AdditionStrategy;
import ntd.calculator.api.strategy.CalculatorStrategy;
import ntd.calculator.api.strategy.StrategyContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculationServiceTest {

    @Mock
    private StrategyContext strategyContext;

    @Mock
    private RecordService recordService;

    @Mock
    private AccountService accountService;

    @Mock
    private OperationService operationService;

    @InjectMocks
    private CalculationService calculationService;

    private User user;
    private CalculationRequest request;
    private Operation operation;
    private Account account;
    private Record record;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).username("user").build();
        var operationType = OperationType.ADDITION;
        request = new CalculationRequest(operationType, BigDecimal.TEN, BigDecimal.ONE);
        operation = Operation.builder().type(operationType).cost(BigDecimal.valueOf(5)).build();
        account = Account.builder().balance(BigDecimal.valueOf(100)).build();
        record = Record.builder()
                .id(1L)
                .user(user)
                .operation(operation)
                .amount(BigDecimal.valueOf(5))
                .userBalance(BigDecimal.valueOf(95))
                .operationResponse("30.00")
                .date(new Date())
                .build();
    }

    @Test
    void shouldPerformCalculationAndReturnResponse() {
        // Arrange
        var arithmeticOperation = mock(CalculatorStrategy.class);

        when(strategyContext.getStrategy(request.getOperationType())).thenReturn(arithmeticOperation);
        when(operationService.findOperationByOperationType(request.getOperationType())).thenReturn(operation);
        when(arithmeticOperation.calculate(request.getOperand1(), request.getOperand2())).thenReturn(BigDecimal.valueOf(30));
        when(accountService.deductFunds(user, operation.getCost())).thenReturn(account);
        when(recordService.createRecord(user, account, operation, operation.getCost(), "30")).thenReturn(record);

        // Act
        var response = calculationService.performOperation(request, user);

        // Assert
        assertNotNull(response);
        assertEquals(record.getId().toString(), response.getId());
        assertEquals(record.getOperationResponse(), response.getOperationResponse());
        assertEquals(record.getDate().toString(), response.getDate());
        assertEquals(record.getAmount().toString(), response.getAmount());
        assertEquals(record.getUserBalance().toString(), response.getUserBalance());
        assertEquals(record.getOperation().getType(), response.getOperationType());

        verifyCommonInteractions();
    }

    @Test
    void shouldThrowExceptionWhenUserHasInsufficientFunds() {
        // Arrange
        doThrow(new InsufficientFundsException("Insufficient funds"))
                .when(accountService)
                .checkUserHasEnoughFunds(user);

        // Act & Assert
        var exception = assertThrows(InsufficientFundsException.class, () -> {
            calculationService.performOperation(request, user);
        });

        assertEquals("Insufficient funds", exception.getMessage());
        verify(accountService).checkUserHasEnoughFunds(user);
        verify(strategyContext, never()).getStrategy(any());
        verify(operationService, never()).findOperationByOperationType(any());
        verify(accountService, never()).deductFunds(any(), any());
        verify(recordService, never()).createRecord(any(), any(), any(), any(), any());
    }

    @Test
    void shouldThrowExceptionWhenUserHasInsufficientFundsForOperation() {
        // Arrange
        var insufficientOperation = Operation.builder().type(OperationType.ADDITION).cost(BigDecimal.TEN).build();
        when(operationService.findOperationByOperationType(OperationType.ADDITION)).thenReturn(insufficientOperation);
        when(strategyContext.getStrategy(OperationType.ADDITION)).thenReturn(new AdditionStrategy());
        doThrow(new InsufficientFundsException("Insufficient funds"))
                .when(accountService)
                .deductFunds(user, BigDecimal.TEN);

        // Act & Assert
        var exception = assertThrows(InsufficientFundsException.class, () -> {
            calculationService.performOperation(request, user);
        });

        assertEquals("Insufficient funds", exception.getMessage());
        verify(recordService, never()).createRecord(any(), any(), any(), any(), any());
    }

    private void verifyCommonInteractions() {
        verify(accountService).checkUserHasEnoughFunds(user);
        verify(strategyContext).getStrategy(request.getOperationType());
        verify(operationService).findOperationByOperationType(request.getOperationType());
        verify(accountService).deductFunds(user, operation.getCost());
        verify(recordService).createRecord(user, account, operation, operation.getCost(), "30");
    }
}
