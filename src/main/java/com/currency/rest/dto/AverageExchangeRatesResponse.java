package com.currency.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.currency.domain.dto.AverageExchangeRates;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@Slf4j
public class AverageExchangeRatesResponse implements Serializable {

    private String from;
    private String to;
    @JsonInclude(NON_NULL)
    private String start_at;
    @JsonInclude(NON_NULL)
    private String end_at;
    private BigDecimal average;

    public static AverageExchangeRatesResponse from(AverageExchangeRates averageExchangeRates) {
        log.info("AverageExchangeRates: {}", averageExchangeRates);
        return AverageExchangeRatesResponse
            .builder()
            .from(averageExchangeRates.getFrom())
            .to(averageExchangeRates.getTo())
            .start_at(averageExchangeRates.getStartDate())
            .end_at(averageExchangeRates.getEndDate())
            .average(averageExchangeRates.getAverage())
            .build();
    }
}
