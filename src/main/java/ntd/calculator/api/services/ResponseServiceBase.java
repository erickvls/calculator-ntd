package ntd.calculator.api.services;

import ntd.calculator.api.models.record.Record;
import ntd.calculator.api.models.responses.CalculatorResponse;
import ntd.calculator.api.models.responses.RecordResponse;

public abstract class ResponseServiceBase {
    public CalculatorResponse createCalculatorResponse(Record recordResult) {
        return CalculatorResponse.builder()
                .id(recordResult.getId().toString())
                .operationResponse(recordResult.getOperationResponse())
                .date(recordResult.getDate().toString())
                .amount(recordResult.getAmount().toString())
                .userBalance(recordResult.getUserBalance().toString())
                .operationType(recordResult.getOperation().getType())
                .build();
    }

    public RecordResponse createRecordResponse(Record recordResult) {
        return RecordResponse.builder()
                .id(recordResult.getId().toString())
                .operationResponse(recordResult.getOperationResponse())
                .date(recordResult.getDate().toString())
                .userBalance(recordResult.getUserBalance().toString())
                .operationType(recordResult.getOperation().getType())
                .build();
    }
}
