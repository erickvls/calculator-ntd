package ntd.calculator.api.services;

import lombok.RequiredArgsConstructor;
import ntd.calculator.api.enums.OperationType;
import ntd.calculator.api.models.responses.CalculatorResponse;
import ntd.calculator.api.models.user.User;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RandomStringService extends ResponseServiceBase {

    private final RandomStringGeneratorClient stringGeneratorClient;
    private final AccountService accountService;
    private final OperationService operationService;
    private final RecordService recordService;

    public CalculatorResponse generateRandomString(User userRequest) throws IOException, InterruptedException {

        // First of all, checks if user has funds
        accountService.checkUserHasEnoughFunds(userRequest);

        // call the api for generating string
        var randomString = stringGeneratorClient.generateString();

        // Get the operation (cost) from the service
        var operation = operationService.findOperationByOperationType(OperationType.RANDOM_STRING);

        // Deduct funds from user account
        var account = accountService.deductFunds(userRequest, operation.getCost());

        // Register the operation in record table
        var recordResult = recordService.createRecord(userRequest, account, operation, operation.getCost(), randomString);

        return createCalculatorResponse(recordResult);
    }
}
