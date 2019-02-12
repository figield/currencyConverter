package com.currency.rest.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
public class ErrorResponse {

    @NonNull
    List<String> messages;

    public static ErrorResponse of(String errorMessage) {
        return ErrorResponse.builder()
                            .messages(Collections.singletonList(errorMessage))
                            .build();
    }

    static ErrorResponse of(List<String> errorMessage) {
        return ErrorResponse.builder()
                            .messages(errorMessage)
                            .build();
    }

    public static ErrorResponse from(MethodArgumentNotValidException exception) {
        List<String> validationErrors = exception.getBindingResult()
                                                 .getFieldErrors()
                                                 .stream()
                                                 .map(error -> error.getField() + " " + error.getDefaultMessage())
                                                 .collect(Collectors.toList());
        return of(validationErrors);
    }
}