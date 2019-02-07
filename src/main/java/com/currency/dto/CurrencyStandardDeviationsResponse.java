package com.currency.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
public class CurrencyStandardDeviationsResponse implements Serializable {

    private String base;
    @JsonInclude(NON_NULL)
    private String start_at;
    @JsonInclude(NON_NULL)
    private String end_at;
    @JsonProperty("rates_standard_deviation")
    private Map<String, BigDecimal> ratesStandardDeviation;

}
