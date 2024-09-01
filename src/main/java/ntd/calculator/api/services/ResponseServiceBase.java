package ntd.calculator.api.services;

import ntd.calculator.api.models.record.Record;
import ntd.calculator.api.models.responses.CalculatorResponse;

public abstract class ResponseServiceBase {
    public CalculatorResponse createResponse(Record recordResult) {
        return CalculatorResponse.builder()
                .id(recordResult.getId().toString())
                .operationResponse(recordResult.getOperationResponse())
                .date(recordResult.getDate().toString())
                .amount(recordResult.getAmount().toString())
                .userBalance(recordResult.getUserBalance().toString())
                .operationType(recordResult.getOperation().getType())
                .build();
    }
}
