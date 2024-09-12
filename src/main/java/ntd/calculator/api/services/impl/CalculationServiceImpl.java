package ntd.calculator.api.services.impl;

import lombok.RequiredArgsConstructor;
import ntd.calculator.api.models.requests.CalculationRequest;
import ntd.calculator.api.models.responses.CalculatorResponse;
import ntd.calculator.api.models.user.User;
import ntd.calculator.api.services.*;
import ntd.calculator.api.strategy.StrategyContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculationServiceImpl extends ResponseServiceBase implements CalculationService {

    private final StrategyContext strategyContext;
    private final RecordService recordService;
    private final AccountService accountService;
    private final OperationService operationService;

    @Override
    public CalculatorResponse performOperation(CalculationRequest calculationRequest, User userRequest) {

        // First of all, checks if user has funds
        accountService.checkUserHasEnoughFunds(userRequest);

        // Define what type of operation the user requires
        var arithmeticOperation = strategyContext.getStrategy(calculationRequest.getOperationType());

        // Get the operation (cost) from the service
        var operation = operationService.findOperationByOperationType(calculationRequest.getOperationType());

        // Get the arithmetic result
        var result = arithmeticOperation.calculate(calculationRequest.getOperand1(), calculationRequest.getOperand2());

        // Deduct funds from user account
        var account = accountService.deductFunds(userRequest, operation.getCost());

        // Register the operation in record table
        var recordResult = recordService.createRecord(userRequest, account, operation, operation.getCost(), result.toString());

        return createCalculatorResponse(recordResult);
    }

}
