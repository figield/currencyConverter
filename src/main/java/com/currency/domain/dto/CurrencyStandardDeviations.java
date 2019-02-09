package com.currency.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class CurrencyStandardDeviations {

    private String base;
    private String startDate;
    private String endDate;
    private Map<String, BigDecimal> ratesStandardDeviation;

}
