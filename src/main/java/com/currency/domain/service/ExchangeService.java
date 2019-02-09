package com.currency.domain.service;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

import com.currency.domain.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponentsBuilder;

@Value
@RequiredArgsConstructor
@Slf4j
public class ExchangeService {

    private RestOperation restOperation;
    private String BASE_URL;

    public ExchangeRates latest(String base) {

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/latest")
                .queryParam("base", base)
                .build()
                .encode()
                .toUri();

        return restOperation.getForObject(targetUrl, ExchangeRates.class);
    }

    public CurrencyConvertion convert(BigDecimal fromAmount, String fromCurrency, String toCurrency) {

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/latest")
                .queryParam("base", fromCurrency)
                //.queryParam("symbols", fromCurrency + "," + toCurrency) //https://api.exchangeratesapi.io/latest?symbols=EUR,PLN&base=EUR
                .build()
                .encode()
                .toUri();

        ExchangeRates exchangeRates = restOperation.getForObject(targetUrl, ExchangeRates.class);

        BigDecimal rateValue = exchangeRates.getRates().get(toCurrency);
        BigDecimal exchangeValue = ExchangeOperations.getMultiplicationValue(rateValue, fromAmount);

        return CurrencyConvertion
                .builder()
                .fromAmount(fromAmount)
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .toAmount(exchangeValue)
                .build();
    }


    public AverageExchangeRates average(String fromCurrency, String toCurrency, String startDate, String endDate) {

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/history")
                .queryParam("base", fromCurrency)
                .queryParam("symbols", fromCurrency + "," + toCurrency)
                .queryParam("start_at", startDate)
                .queryParam("end_at", endDate)
                .build()
                .encode()
                .toUri();

        HistoricalExchangeRates historicalExchangeRates = restOperation.getForObject(targetUrl, HistoricalExchangeRates.class);

        BigDecimal average = ExchangeOperations.getAverageForCurrencyValue(
                toCurrency,
                Optional.of(historicalExchangeRates.getRates()));

        return AverageExchangeRates.builder()
                .startDate(startDate)
                .endDate(endDate)
                .from(fromCurrency)
                .to(toCurrency)
                .average(average)
                .build();
    }

    public CurrencyStandardDeviations standard_deviation(String base, String startDate, String endDate) {

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/history")
                .queryParam("base", base)
                .queryParam("start_at", startDate)
                .queryParam("end_at", endDate)
                .build()
                .encode()
                .toUri();

        HistoricalExchangeRates historicalExchangeRates = restOperation.getForObject(targetUrl, HistoricalExchangeRates.class);

        Map<String, BigDecimal> ratesStandardDeviation = ExchangeOperations
                .getStandardDeviationValues(Optional.of(historicalExchangeRates.getRates()));


        return CurrencyStandardDeviations.builder()
                .startDate(startDate)
                .endDate(endDate)
                .base(base)
                .ratesStandardDeviation(ratesStandardDeviation)
                .build();
    }

}
