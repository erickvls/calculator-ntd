package ntd.calculator.api.controllers;

import lombok.RequiredArgsConstructor;
import ntd.calculator.api.models.requests.CalculationRequest;
import ntd.calculator.api.models.responses.CalculatorResponse;
import ntd.calculator.api.models.responses.RecordResponse;
import ntd.calculator.api.models.user.User;
import ntd.calculator.api.services.CalculationService;
import ntd.calculator.api.services.RandomStringService;
import ntd.calculator.api.services.RecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RecordResponse>> records(@AuthenticationPrincipal User user)  {
        var result = recordService.findRecordsByUser(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}
