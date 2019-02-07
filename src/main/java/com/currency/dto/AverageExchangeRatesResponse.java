package com.currency.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
public class AverageExchangeRatesResponse implements Serializable {

    private String base;
    @JsonInclude(NON_NULL)
    private String start_at;
    @JsonInclude(NON_NULL)
    private String end_at;
    private BigDecimal average;
}
