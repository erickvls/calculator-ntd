package ntd.calculator.api.services;

import ntd.calculator.api.models.responses.CalculatorResponse;
import ntd.calculator.api.models.user.User;

import java.io.IOException;

public interface RandomStringService {
    CalculatorResponse generateRandomString(User userRequest) throws IOException, InterruptedException;
}
