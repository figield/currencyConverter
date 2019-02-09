package com.currency.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CurrencyConvertion {

    private String date;
    private BigDecimal fromAmount;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal toAmount;

}
