package ntd.calculator.api.services;

import ntd.calculator.api.enums.OperationType;
import ntd.calculator.api.exceptions.InsufficientFundsException;
import ntd.calculator.api.models.account.Account;
import ntd.calculator.api.models.operation.Operation;
import ntd.calculator.api.models.record.Record;
import ntd.calculator.api.models.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RandomStringServiceTest {

    @Mock
    private RandomStringGeneratorClient stringGeneratorClient;

    @Mock
    private AccountService accountService;

    @Mock
    private OperationService operationService;

    @Mock
    private RecordService recordService;

    @InjectMocks
    private RandomStringService randomStringService;

    private User user;
    private Operation operation;
    private Account account;
    private Record record;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).username("user").build();
        operation = Operation.builder().type(OperationType.RANDOM_STRING).cost(BigDecimal.valueOf(5)).build();
        account = Account.builder().balance(BigDecimal.valueOf(100)).build();
        record = Record.builder()
                .id(1L)
                .user(user)
                .operation(operation)
                .amount(BigDecimal.valueOf(5))
                .userBalance(BigDecimal.valueOf(95))
                .operationResponse("randomString")
                .date(new Date())
                .build();
    }

    @Test
    void shouldGenerateStringAndReturnResponse() throws IOException, InterruptedException {
        var randomString = "randomString";

        when(stringGeneratorClient.generateString()).thenReturn(randomString);
        when(operationService.findOperationByOperationType(OperationType.RANDOM_STRING)).thenReturn(operation);
        when(accountService.deductFunds(user, operation.getCost())).thenReturn(account);
        when(recordService.createRecord(user, account, operation, operation.getCost(), randomString)).thenReturn(record);

        var response = randomStringService.generateRandomString(user);

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
        doThrow(new InsufficientFundsException("Insufficient funds")).when(accountService).checkUserHasEnoughFunds(user);

        assertThrows(InsufficientFundsException.class, () -> randomStringService.generateRandomString(user));
        verify(accountService).checkUserHasEnoughFunds(user);
        verifyNoInteractions(stringGeneratorClient);
        verifyNoInteractions(operationService);
        verifyNoInteractions(recordService);
    }

    @Test
    void shouldThrowIOExceptionWhenRandomStringClientFails() throws IOException, InterruptedException {
        when(stringGeneratorClient.generateString()).thenThrow(new IOException("Client IO Error"));

        var exception = assertThrows(IOException.class, () -> randomStringService.generateRandomString(user));

        assertEquals("Client IO Error", exception.getMessage());
        verify(accountService).checkUserHasEnoughFunds(user);
        verify(stringGeneratorClient).generateString();
        verifyNoMoreInteractions(operationService);
        verifyNoInteractions(recordService);
    }

    @Test
    void shouldThrowClientInterruptedExceptionWhenRandomStringClientFails() throws IOException, InterruptedException {
        when(stringGeneratorClient.generateString()).thenThrow(new InterruptedException("Client Interrupted Error"));

        InterruptedException exception = assertThrows(InterruptedException.class, () -> randomStringService.generateRandomString(user));
        assertEquals("Client Interrupted Error", exception.getMessage());
        verify(accountService).checkUserHasEnoughFunds(user);
        verify(stringGeneratorClient).generateString();
        verifyNoInteractions(operationService);
        verifyNoInteractions(recordService);
    }

    private void verifyCommonInteractions() throws IOException, InterruptedException {
        verify(accountService).checkUserHasEnoughFunds(user);
        verify(stringGeneratorClient).generateString();
        verify(operationService).findOperationByOperationType(OperationType.RANDOM_STRING);
        verify(accountService).deductFunds(user, operation.getCost());
        verify(recordService).createRecord(user, account, operation, operation.getCost(), "randomString");
    }

}