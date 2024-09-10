package ntd.calculator.api.controllers;

import lombok.RequiredArgsConstructor;
import ntd.calculator.api.models.account.Account;
import ntd.calculator.api.models.responses.AccountInfoResponse;
import ntd.calculator.api.models.user.User;
import ntd.calculator.api.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountInfoResponse> accountInformation(
            @AuthenticationPrincipal User user)  {
        var result = accountService.findAccountInfo(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}
