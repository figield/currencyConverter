package com.currency.api.controllers;


import java.math.BigDecimal;

import com.currency.api.dto.AverageExchangeRatesResponse;
import com.currency.api.dto.CurrencyConvertionResponse;
import com.currency.api.dto.CurrencyStandardDeviationsResponse;
import com.currency.api.dto.LatestExchangeRatesResponse;
import com.currency.domain.dto.AverageExchangeRates;
import com.currency.domain.dto.CurrencyConvertion;
import com.currency.domain.dto.CurrencyStandardDeviations;
import com.currency.domain.dto.ExchangeRates;
import com.currency.domain.service.ExchangeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// TODO: fix problem with EURO to EURO currency conversion
// TODO: convert date from 12/27/2017 to 2017-12-27
// TODO: move configuration to property, ex: URL=https://api.exchangeratesapi.io
@RestController
@AllArgsConstructor
@Slf4j
public class CurrencyController {

    private final ExchangeService exchangeService;

    @Cacheable(value = "latest")
    @RequestMapping("/latest")
    @GetMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public LatestExchangeRatesResponse latest(
        @RequestParam(value = "base", defaultValue = "EUR") String base) {

        ExchangeRates exchangeRates = exchangeService.latest(base);

        log.info("ExchangeRates: {}", exchangeRates);

        return LatestExchangeRatesResponse.from(exchangeRates);
    }

    @Cacheable(value = "convert")
    @RequestMapping("/convert")
    @GetMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public CurrencyConvertionResponse convert(
        @RequestParam(value = "fromamount", defaultValue = "1") BigDecimal fromamount,
        @RequestParam(value = "from", defaultValue = "USD") String fromCurrency,
        @RequestParam(value = "to", defaultValue = "PLN") String toCurrency) {

        CurrencyConvertion currencyConvertion = exchangeService.convert(fromamount, fromCurrency, toCurrency);

        log.info("CurrencyConvertion: {}", currencyConvertion);

        return CurrencyConvertionResponse.from(currencyConvertion);
    }

    @Cacheable(value = "average")
    @RequestMapping("/average")
    @GetMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public AverageExchangeRatesResponse average(
        @RequestParam(value = "from", defaultValue = "USD") String fromCurrency,
        @RequestParam(value = "to", defaultValue = "PLN") String toCurrency,
        @RequestParam(value = "start_at") String startDate,
        @RequestParam(value = "end_at") String endDate) {

        AverageExchangeRates averageExchangeRates = exchangeService.average(fromCurrency, toCurrency, startDate, endDate);

        log.info("AverageExchangeRates: {}", averageExchangeRates);

        return AverageExchangeRatesResponse.from(averageExchangeRates);
    }

    @Cacheable(value = "standarddeviation")
    @RequestMapping("/sd")
    @GetMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public CurrencyStandardDeviationsResponse standard_deviation(
        @RequestParam(value = "base", defaultValue = "EUR") String base,
        @RequestParam(value = "start_at") String startDate,
        @RequestParam(value = "end_at") String endDate) {

        CurrencyStandardDeviations currencyStandardDeviations = exchangeService.standard_deviation(base, startDate, endDate);

        log.info("CurrencyStandardDeviations: {}", currencyStandardDeviations);

        return CurrencyStandardDeviationsResponse.from(currencyStandardDeviations);
    }


}