package ntd.calculator.api.controllers;

import lombok.RequiredArgsConstructor;
import ntd.calculator.api.models.responses.OperationResponse;
import ntd.calculator.api.models.responses.RecordResponse;
import ntd.calculator.api.models.user.User;
import ntd.calculator.api.services.OperationService;
import ntd.calculator.api.services.RecordService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/operations")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OperationResponse>> records()  {
        var result = operationService.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}
