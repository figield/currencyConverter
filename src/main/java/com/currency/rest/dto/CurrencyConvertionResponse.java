package com.currency.rest.dto;

import com.currency.domain.dto.CurrencyConvertion;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@Slf4j
public class CurrencyConvertionResponse implements Serializable {

    @JsonInclude(NON_NULL)
    private String date;
    private BigDecimal fromAmount;
    private String from;
    private BigDecimal toAmount;
    private String to;


    public static CurrencyConvertionResponse from(CurrencyConvertion currencyConvertion) {
        log.info("CurrencyConvertion: {}", currencyConvertion);
        return CurrencyConvertionResponse
            .builder()
            .date(currencyConvertion.getDate())
            .fromAmount(currencyConvertion.getFromAmount())
            .from(currencyConvertion.getFromCurrency())
            .toAmount(currencyConvertion.getToAmount())
            .to(currencyConvertion.getToCurrency())
            .build();
    }
}
