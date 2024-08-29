package ntd.calculator.api.controllers;

import lombok.RequiredArgsConstructor;
import ntd.calculator.api.models.requests.CalculationRequest;
import ntd.calculator.api.models.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculator")
@RequiredArgsConstructor
public class CalculatorController {
    @PostMapping(value = "/calculate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> calculate(
            @RequestBody CalculationRequest request,
            @AuthenticationPrincipal User user) {
        //perform some action
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("ok");
    }
}
