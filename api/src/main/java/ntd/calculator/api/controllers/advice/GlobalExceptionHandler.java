package ntd.calculator.api.controllers.advice;

import ntd.calculator.api.models.responses.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static ntd.calculator.api.enums.ErrorType.AUTHENTICATION_ERROR;
import static ntd.calculator.api.enums.ErrorType.VALIDATION_ERROR;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorResponse.ErrorDetails.FieldError> fieldErrors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.add(new ErrorResponse.ErrorDetails.FieldError(fieldName, errorMessage));
        });

        var errorDetails = new ErrorResponse.ErrorDetails(VALIDATION_ERROR.getType(), fieldErrors);
        var errorResponse = new ErrorResponse(errorDetails);

        return buildResponse(BAD_REQUEST, errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        List<ErrorResponse.ErrorDetails.FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new ErrorResponse.ErrorDetails.FieldError("credentials", "Invalid username or password"));

        var errorDetails = new ErrorResponse.ErrorDetails(AUTHENTICATION_ERROR.getType(), fieldErrors);
        var errorResponse = new ErrorResponse(errorDetails);
        return buildResponse(UNAUTHORIZED, errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return buildResponse(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        List<ErrorResponse.ErrorDetails.FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new ErrorResponse.ErrorDetails.FieldError("credentials", "An user with the email already exists"));

        var errorDetails = new ErrorResponse.ErrorDetails(VALIDATION_ERROR.getType(), fieldErrors);
        var errorResponse = new ErrorResponse(errorDetails);
        return buildResponse(HttpStatus.BAD_REQUEST, errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        List<ErrorResponse.ErrorDetails.FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new ErrorResponse.ErrorDetails.FieldError("credentials", "Access Denied"));

        var errorDetails = new ErrorResponse.ErrorDetails(AUTHENTICATION_ERROR.getType(), fieldErrors);
        var errorResponse = new ErrorResponse(errorDetails);
        return buildResponse(HttpStatus.FORBIDDEN, errorResponse);
    }


    private <T> ResponseEntity<T> buildResponse(HttpStatus status, T body) {
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }
}
