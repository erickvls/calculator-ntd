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
public class OperationResponse {
    private OperationType operationType;
    private String cost;
}
