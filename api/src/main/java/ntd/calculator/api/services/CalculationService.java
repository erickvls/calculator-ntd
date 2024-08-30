package ntd.calculator.api.services;

import lombok.RequiredArgsConstructor;
import ntd.calculator.api.models.requests.CalculationRequest;
import ntd.calculator.api.models.user.User;
import ntd.calculator.api.strategy.StrategyContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculationService {

    private final StrategyContext strategyContext;
    private final RecordService recordService;
    private final AccountService accountService;
    private final OperationService operationService;

    public String performOperation(CalculationRequest calculationRequest, User userRequest) {

        // Get the operation
        var arithmeticOperation = strategyContext.getStrategy(calculationRequest.getOperationType());

        // Get the cost of the operation from the service
        var operation = operationService.findOperationByOperationType(calculationRequest.getOperationType());

        // Get the arithmetic result
        var result = arithmeticOperation.calculate(calculationRequest.getOperand1(), calculationRequest.getOperand2());

        // Deduct funds from user account
        var account = accountService.deductFunds(userRequest, operation.getCost());

        // Register the operation in record table
        var record = recordService.createRecord(userRequest, account, operation, operation.getCost(), result);

        return result.toString();
    }

}
