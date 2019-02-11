package com.currency.rest.controllers;

import com.currency.rest.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private static Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> validationExceptionHandler(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ErrorResponse.from(exception));
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorResponse> missingParamExceptionHandler(MissingServletRequestParameterException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ErrorResponse.of(exception.getMessage()));
    }

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<ErrorResponse> httpClientExceptionHandler(HttpClientErrorException exception) {

        log.warn("HTTP client exception: ", exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ErrorResponse.of(exception.getMessage()));
    }


    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity jsonSerializationExceptionHandler(HttpMessageNotReadableException exception) {

        log.warn("Unable to serialize/deserialize", exception);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity internalServerErrorHandler(Throwable throwable) {

        log.error("Fatal error", throwable);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ErrorResponse.of("Server is unable to process the request."));
    }
}
