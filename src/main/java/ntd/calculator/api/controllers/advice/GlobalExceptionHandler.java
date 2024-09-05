package ntd.calculator.api.controllers.advice;

import ntd.calculator.api.exceptions.AccountNotFoundException;
import ntd.calculator.api.exceptions.InsufficientFundsException;
import ntd.calculator.api.exceptions.OperationNotFoundException;
import ntd.calculator.api.models.responses.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static ntd.calculator.api.enums.ErrorType.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseGlobalExceptionHandler {

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<ErrorResponse> handleArithmeticException(ArithmeticException ex) {
        List<ErrorResponse.ErrorDetails.FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new ErrorResponse.ErrorDetails.FieldError(FIELD_TYPE_OPERATION, ex.getMessage()));

        var errorDetails = new ErrorResponse.ErrorDetails(OPERATION_ERROR.getType(), fieldErrors);
        var errorResponse = new ErrorResponse(errorDetails);
        return buildResponse(HttpStatus.NOT_FOUND, errorResponse);
    }

    @ExceptionHandler(OperationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOperationNotFoundException(OperationNotFoundException ex) {
        List<ErrorResponse.ErrorDetails.FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new ErrorResponse.ErrorDetails.FieldError(FIELD_TYPE_OPERATION, ex.getMessage()));

        var errorDetails = new ErrorResponse.ErrorDetails(OPERATION_ERROR.getType(), fieldErrors);
        var errorResponse = new ErrorResponse(errorDetails);
        return buildResponse(HttpStatus.NOT_FOUND, errorResponse);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFundsException(InsufficientFundsException ex) {
        List<ErrorResponse.ErrorDetails.FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new ErrorResponse.ErrorDetails.FieldError(FIELD_TYPE_FUNDS, ex.getMessage()));

        var errorDetails = new ErrorResponse.ErrorDetails(BALANCE_ERROR.getType(), fieldErrors);
        var errorResponse = new ErrorResponse(errorDetails);
        return buildResponse(HttpStatus.BAD_REQUEST, errorResponse);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException ex) {
        List<ErrorResponse.ErrorDetails.FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new ErrorResponse.ErrorDetails.FieldError(FIELD_TYPE_ACCOUNT, ex.getMessage()));

        var errorDetails = new ErrorResponse.ErrorDetails(ACCOUNT_ERROR.getType(), fieldErrors);
        var errorResponse = new ErrorResponse(errorDetails);
        return buildResponse(HttpStatus.NOT_FOUND, errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorResponse.ErrorDetails.FieldError> fieldErrors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError) {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                fieldErrors.add(new ErrorResponse.ErrorDetails.FieldError(fieldName, errorMessage));
            }else{
                var objectName = error.getObjectName();
                var errorMessage = error.getDefaultMessage();
                fieldErrors.add(new ErrorResponse.ErrorDetails.FieldError(objectName, errorMessage));
            }
        });

        var errorDetails = new ErrorResponse.ErrorDetails(VALIDATION_ERROR.getType(), fieldErrors);
        var errorResponse = new ErrorResponse(errorDetails);

        return buildResponse(BAD_REQUEST, errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        List<ErrorResponse.ErrorDetails.FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new ErrorResponse.ErrorDetails.FieldError(FIELD_TYPE_CREDENTIALS, "Invalid username or password"));

        var errorDetails = new ErrorResponse.ErrorDetails(AUTHENTICATION_ERROR.getType(), fieldErrors);
        var errorResponse = new ErrorResponse(errorDetails);
        return buildResponse(UNAUTHORIZED, errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        List<ErrorResponse.ErrorDetails.FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new ErrorResponse.ErrorDetails.FieldError(FIELD_TYPE_CREDENTIALS, "An user with the email already exists"));

        var errorDetails = new ErrorResponse.ErrorDetails(VALIDATION_ERROR.getType(), fieldErrors);
        var errorResponse = new ErrorResponse(errorDetails);
        return buildResponse(CONFLICT, errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        List<ErrorResponse.ErrorDetails.FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new ErrorResponse.ErrorDetails.FieldError(FIELD_TYPE_CREDENTIALS, "Access Denied"));

        var errorDetails = new ErrorResponse.ErrorDetails(AUTHENTICATION_ERROR.getType(), fieldErrors);
        var errorResponse = new ErrorResponse(errorDetails);
        return buildResponse(HttpStatus.FORBIDDEN, errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return buildResponse(INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
