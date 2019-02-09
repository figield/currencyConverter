package com.currency.api.dto;

import com.currency.domain.dto.ExchangeRates;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LatestExchangeRatesResponse implements Serializable {

    private String base;
    @JsonInclude(NON_NULL)
    private String date;
    private Map<String, BigDecimal> rates;

    public static LatestExchangeRatesResponse from(ExchangeRates exchangeRates){
        return LatestExchangeRatesResponse
                .builder()
                .base(exchangeRates.getBase())
                .date(exchangeRates.getDate())
                .rates(exchangeRates.getRates())
                .build();
    }

}
