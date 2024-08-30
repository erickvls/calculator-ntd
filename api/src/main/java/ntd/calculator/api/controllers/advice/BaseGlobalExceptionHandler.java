package ntd.calculator.api.controllers.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class BaseGlobalExceptionHandler {
    protected static final String FIELD_TYPE_CREDENTIALS = "credentials";
    protected static final String FIELD_TYPE_FUNDS = "funds";
    protected static final String FIELD_TYPE_OPERATION = "operation";
    protected static final String FIELD_TYPE_ACCOUNT = "account";

    protected <T> ResponseEntity<T> buildResponse(HttpStatus status, T body) {
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }
}
