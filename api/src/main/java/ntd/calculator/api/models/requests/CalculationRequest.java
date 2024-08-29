package ntd.calculator.api.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ntd.calculator.api.enums.OperationType;

import java.math.BigDecimal;

@Data
public class CalculationRequest {
    @NotNull
    private OperationType operationType;

    @NotNull
    private BigDecimal operand1;

    @NotNull
    private BigDecimal operand2;
}
