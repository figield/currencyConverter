package com.currency.controllers;


import java.math.BigDecimal;

import com.currency.dto.AverageExchangeRatesResponse;
import com.currency.dto.ExchangeRates;
import com.currency.dto.HistoricalExchangeRates;
import com.currency.service.ExchangeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// problem with EURO currency conversion

@RestController
@AllArgsConstructor
@Slf4j
public class CurrencyController {

    private final ExchangeService exchangeService;


    @RequestMapping("/latest")
    @GetMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ExchangeRates latest(
        @RequestParam(value = "base", defaultValue = "EUR") String base) {

        ExchangeRates es = exchangeService.latest(base);

        log.info("{}", es);

        return es;
    }

    @Cacheable(value = "convert")
    @RequestMapping("/convert")
    @GetMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ExchangeRates convert(
        @RequestParam(value = "fromamount", defaultValue = "1") BigDecimal fromamount,
        @RequestParam(value = "from", defaultValue = "USD") String from,
        @RequestParam(value = "to", defaultValue = "PLN") String to) {

        ExchangeRates es = exchangeService.convert(fromamount, from, to);

        log.info("{}", es);

        return es;
    }

    @Cacheable(value = "average")
    @RequestMapping("/average")
    @GetMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public AverageExchangeRatesResponse average(
        @RequestParam(value = "from", defaultValue = "USD") String from,
        @RequestParam(value = "to", defaultValue = "PLN") String to,
        @RequestParam(value = "start_at") String startDate,
        @RequestParam(value = "end_at") String endDate) {

        AverageExchangeRatesResponse es = exchangeService.average(from, to, startDate, endDate);

        log.info("{}", es);

        return es;
    }

}