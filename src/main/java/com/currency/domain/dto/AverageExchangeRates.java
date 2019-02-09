package com.currency.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AverageExchangeRates {

    private String from;
    private String to;
    private String startDate;
    private String endDate;
    private BigDecimal average;

}
