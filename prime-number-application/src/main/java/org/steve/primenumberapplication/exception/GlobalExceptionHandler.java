package org.steve.primenumberapplication.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.steve.primenumberapplication.config.PrimeNumberConfiguration;
import org.steve.primenumberapplication.model.ErrorResponse;
import org.steve.primenumberapplication.model.PrimeAlgorithm;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final int maximumInputValue;

    public GlobalExceptionHandler(PrimeNumberConfiguration primeNumberConfiguration) {
        this.maximumInputValue = primeNumberConfiguration.getMaximumInputValue();
    }

    @ExceptionHandler(InputValueException.class)
    public ResponseEntity<ErrorResponse> inputValidationFailureInputValue(InputValueException exception){
        log.warn(exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(createErrorMessageInputValue(exception.getInputValue()));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> inputValidationFailureAlgorithm(MethodArgumentTypeMismatchException exception) {
        log.warn(exception.getMessage());

        if (exception.getRequiredType() == PrimeAlgorithm.class){

            String message = createErrorMessage(exception.getValue(), exception.getName(),Arrays.toString(PrimeAlgorithm.values()));

            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(message));
        }

        ///this should just be inputValue errors
        ErrorResponse errorResponse = new ErrorResponse(createErrorMessageInputValue(exception.getValue()));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private String createErrorMessageInputValue(Object value) {
        String validValues = String.format("values in range [0 - %d]", maximumInputValue);
        return createErrorMessage(value, "inputValue", validValues);
    }

    private String createErrorMessage(Object value, String parameterName, String validValues) {
        return String.format("Invalid value '%s' for parameter '%s'. Supported values are %s",
                value,
                parameterName,
                validValues
        );
    }

}
