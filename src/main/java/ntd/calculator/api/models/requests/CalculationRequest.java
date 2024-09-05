package ntd.calculator.api.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import ntd.calculator.api.enums.OperationType;
import ntd.calculator.api.validation.SecondOperandRequired;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@SecondOperandRequired
public class CalculationRequest {
    @NotNull(message = "Operation type cannot be empty")
    private OperationType operationType;

    @NotNull(message = "First operand is required")
    private BigDecimal operand1;

    // This field is conditionally required
    private BigDecimal operand2;
}
