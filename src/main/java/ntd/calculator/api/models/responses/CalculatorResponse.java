package ntd.calculator.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ntd.calculator.api.enums.OperationType;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CalculatorResponse {
    private String id;
    private OperationType operationType;
    private String amount;
    private String userBalance;
    private String operationResponse;
    private String date;
}
