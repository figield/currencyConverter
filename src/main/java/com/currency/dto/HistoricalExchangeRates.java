package com.currency.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoricalExchangeRates implements Serializable {

    private String base;
    private String start_at;
    private String end_at;
    private Map<String, Map<String, BigDecimal>> rates = new HashMap<>();


}

