package com.currency.rest.controllers;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Size;

import com.currency.rest.dto.AverageExchangeRatesResponse;
import com.currency.rest.dto.CurrencyConvertionResponse;
import com.currency.rest.dto.CurrencyStandardDeviationsResponse;
import com.currency.rest.dto.LatestExchangeRatesResponse;
import com.currency.domain.service.ExchangeService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// TODO: move configuration to property, ex: URL=https://api.exchangeratesapi.io

@RestController
@AllArgsConstructor
@Validated
public class CurrencyController {

    private final ExchangeService exchangeService;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final String currencyLengthMsg = "Currency name must be 3 characters long";

    @Cacheable(value = "latest")
    @RequestMapping("/latest")
    @GetMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public LatestExchangeRatesResponse latest(
        @RequestParam(value = "base", defaultValue = "EUR") @Size(max = 3, message = currencyLengthMsg) String base) {

        return LatestExchangeRatesResponse.from(exchangeService.latest(base));
    }

    @Cacheable(value = "convert")
    @RequestMapping("/convert")
    @GetMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public CurrencyConvertionResponse convert(
        @RequestParam(value = "fromamount", defaultValue = "1") BigDecimal fromAmount,
        @RequestParam(value = "from") @Size(min = 3, max = 3, message = currencyLengthMsg) String fromCurrency,
        @RequestParam(value = "to") @Size(min = 3, max = 3, message = currencyLengthMsg) String toCurrency) {

        return CurrencyConvertionResponse.from(
            exchangeService.convert(fromAmount, fromCurrency, toCurrency));
    }

    @Cacheable(value = "average")
    @RequestMapping("/average")
    @GetMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public AverageExchangeRatesResponse average(
        @RequestParam(value = "from") @Size(min = 3, max = 3, message = currencyLengthMsg) String fromCurrency,
        @RequestParam(value = "to") @Size(min = 3, max = 3, message = currencyLengthMsg) String toCurrency,
        @RequestParam(value = "start_at") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
        @RequestParam(value = "end_at") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        return AverageExchangeRatesResponse.from(
            exchangeService.average(
                fromCurrency,
                toCurrency,
                dateFormat.format(startDate),
                dateFormat.format(endDate)));
    }

    @Cacheable(value = "standarddeviation")
    @RequestMapping("/sd")
    @GetMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public CurrencyStandardDeviationsResponse standard_deviation(
        @RequestParam(value = "base", defaultValue = "EUR") @Size(min = 3, max = 3, message = currencyLengthMsg) String base,
        @RequestParam(value = "start_at") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
        @RequestParam(value = "end_at") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        return CurrencyStandardDeviationsResponse.from(
            exchangeService.standard_deviation(
                base,
                dateFormat.format(startDate),
                dateFormat.format(endDate)));
    }

}