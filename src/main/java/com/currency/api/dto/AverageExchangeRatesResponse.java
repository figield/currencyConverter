package com.currency.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.currency.domain.dto.AverageExchangeRates;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
public class AverageExchangeRatesResponse implements Serializable {

    private String from;
    private String to;
    @JsonInclude(NON_NULL)
    private String start_at;
    @JsonInclude(NON_NULL)
    private String end_at;
    private BigDecimal average;

    public static AverageExchangeRatesResponse from(AverageExchangeRates averageExchangeRates){
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
