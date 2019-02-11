package com.currency.rest.controllers;

import javax.validation.ConstraintViolationException;

import com.currency.rest.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private static Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @Order(100)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> validationExceptionHandler(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ErrorResponse.from(exception));
    }

    @Order(100)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorResponse> missingParamExceptionHandler(MissingServletRequestParameterException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ErrorResponse.of(exception.getMessage()));
    }



    @Order(100)
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> httpClientExceptionHandler(ConstraintViolationException exception) {

        log.error("Currency name must be 3 characters long: ", exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ErrorResponse.of(exception.getMessage()));
    }

    @Order(100)
    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<ErrorResponse> httpClientExceptionHandler(HttpClientErrorException exception) {

        log.error("Currency vendor exception: ", exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ErrorResponse.of(exception.getMessage()));
    }

    @Order(100)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> httpClientExceptionHandler(MethodArgumentTypeMismatchException exception) {

        log.error("Argument type mismatch exception:", exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ErrorResponse.of("Argument has wrong format"));
    }

    @Order
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity jsonSerializationExceptionHandler(HttpMessageNotReadableException exception) {

        log.error("Unable to serialize/deserialize", exception);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }

    @Order
    @ExceptionHandler({Throwable.class})
    public ResponseEntity internalServerErrorHandler(Throwable throwable) {

        log.error("Fatal error", throwable);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ErrorResponse.of("Server is unable to process the request."));
    }
}
