package ntd.calculator.api.services;

import ntd.calculator.api.models.requests.CalculationRequest;
import ntd.calculator.api.models.responses.CalculatorResponse;
import ntd.calculator.api.models.user.User;

public interface CalculationService {
    CalculatorResponse performOperation(CalculationRequest calculationRequest, User userRequest);
}
