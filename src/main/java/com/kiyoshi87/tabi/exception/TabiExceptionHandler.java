package com.kiyoshi87.tabi.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static com.kiyoshi87.tabi.exception.TabiExceptionType.INTERNAL_ERROR;
import static com.kiyoshi87.tabi.exception.TabiExceptionType.VALIDATION_ERROR;

@RestControllerAdvice
public class TabiExceptionHandler {

    @ExceptionHandler(TabiApiException.class)
    public ResponseEntity<TabiErrorResponse> handleTabiApiException(TabiApiException ex) {
        HttpStatus status = mapToHttpStatus(ex.getType());

        return new ResponseEntity<>(
                TabiErrorResponse.builder()
                        .error(ex.getType().name())
                        .message(ex.getMessage())
                        .status(status.value())
                        .timestamp(LocalDateTime.now())
                        .build(),
                status);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<TabiErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        return new ResponseEntity<>(
                TabiErrorResponse.builder()
                        .error(VALIDATION_ERROR.name())
                        .message(message)
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<TabiErrorResponse> handleGeneric(Exception ex) {
        return new ResponseEntity<>(
                TabiErrorResponse.builder()
                        .error(INTERNAL_ERROR.name())
                        .message("An unexpected error occurred.")
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private HttpStatus mapToHttpStatus(TabiExceptionType type) {
        return switch (type) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case BAD_REQUEST, VALIDATION_ERROR -> HttpStatus.BAD_REQUEST;
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case FORBIDDEN -> HttpStatus.FORBIDDEN;
            case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
