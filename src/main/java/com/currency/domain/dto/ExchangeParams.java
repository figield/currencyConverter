package com.currency.domain.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExchangeParams {
    private String base;
    private BigDecimal amount;
    private String from;
    private String to;
    private String startDate;
    private String endDate;

    public boolean sameCurrency(){
        return from.equals(to);
    }


}
