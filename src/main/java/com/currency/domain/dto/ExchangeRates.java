package com.currency.domain.dto;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;

@Data
public class ExchangeRates {

    private String base;
    private String date;
    private String startDate;
    private String endDate;
    private Map<String, BigDecimal> rates;

}
