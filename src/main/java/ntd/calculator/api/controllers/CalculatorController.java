package ntd.calculator.api.controllers;

import lombok.RequiredArgsConstructor;
import ntd.calculator.api.models.requests.CalculationRequest;
import ntd.calculator.api.models.responses.CalculatorResponse;
import ntd.calculator.api.models.user.User;
import ntd.calculator.api.services.CalculationService;
import ntd.calculator.api.services.RandomStringService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/calculator")
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculationService calculationService;
    private final RandomStringService randomStringService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalculatorResponse> calculate(
            @RequestBody CalculationRequest request,
            @AuthenticationPrincipal User user) {
        var result = calculationService.performOperation(request, user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping(value = "/generate/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalculatorResponse> generateString(@AuthenticationPrincipal User user) throws IOException, InterruptedException {
        var result = randomStringService.generateRandomString(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}
