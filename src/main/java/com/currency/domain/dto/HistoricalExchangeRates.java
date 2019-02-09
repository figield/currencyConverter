package com.currency.domain.dto;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;

@Data
public class HistoricalExchangeRates {

    private String base;
    private String startDate;
    private String endDate;
    private Map<String, Map<String, BigDecimal>> rates;

}

