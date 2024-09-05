package ntd.calculator.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ntd.calculator.api.enums.OperationType;
import ntd.calculator.api.models.requests.CalculationRequest;

public class SecondOperandValidator implements ConstraintValidator<SecondOperandRequired, CalculationRequest> {

    @Override
    public boolean isValid(CalculationRequest request, ConstraintValidatorContext context) {
        if (request.getOperationType() != OperationType.SQUARE_ROOT
                && request.getOperationType() != OperationType.RANDOM_STRING) {
           return request.getOperand2() != null;
        }
        return true;
    }
}
