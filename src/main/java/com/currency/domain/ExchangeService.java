package com.currency.domain;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.currency.dto.AverageExchangeRatesResponse;
import com.currency.dto.CurrencyStandardDeviationsResponse;
import com.currency.dto.ExchangeRates;
import com.currency.dto.HistoricalExchangeRates;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Value
@RequiredArgsConstructor
@Slf4j
public class ExchangeService {

    private RestTemplate restTemplate;
    private String BASE_URL;

    public ExchangeRates latest(String base) {

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                                            .path("/latest")
                                            .queryParam("base", base)
                                            .build()
                                            .encode()
                                            .toUri();

        return restTemplate.getForObject(targetUrl, ExchangeRates.class);
    }

    public ExchangeRates convert(BigDecimal fromAmount, String fromCurrency, String toCurrency) {

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                                            .path("/latest")
                                            .queryParam("base", fromCurrency)
                                            .queryParam("symbols", fromCurrency + "," + toCurrency)
                                            .build()
                                            .encode()
                                            .toUri();

        ExchangeRates exchangeRates = restTemplate.getForObject(targetUrl, ExchangeRates.class);

        BigDecimal toValue = exchangeRates.getRates().get(toCurrency);

        // create response objects
        // move to operations
        exchangeRates.getRates().put(toCurrency, fromAmount.multiply(toValue));
        exchangeRates.getRates().put(fromCurrency, fromAmount);

        return exchangeRates;
    }

// convert date from 12/27/2017 to 2017-12-27
// GET https://api.exchangeratesapi.io/history?start_at=2018-01-01&end_at=2018-09-01&symbols=ILS,JPY HTTP/1.1

    public AverageExchangeRatesResponse average(String fromCurrency, String toCurrency, String startDate, String endDate) {

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                                            .path("/history")
                                            .queryParam("base", fromCurrency)
                                            .queryParam("symbols", fromCurrency + "," + toCurrency)
                                            .queryParam("start_at", startDate)
                                            .queryParam("end_at", endDate)
                                            .build()
                                            .encode()
                                            .toUri();

        HistoricalExchangeRates historicalExchangeRates = restTemplate.getForObject(targetUrl, HistoricalExchangeRates.class);

        BigDecimal average = ExchangeOperations.getAverageForCurrencyValue(
            toCurrency,
            Optional.of(historicalExchangeRates.getRates()));

        return AverageExchangeRatesResponse.builder()
                                           .start_at(startDate)
                                           .end_at(endDate)
                                           .base(fromCurrency)
                                           .average(average)
                                           .build();
    }

    public CurrencyStandardDeviationsResponse standard_deviation(String base, String startDate, String endDate) {

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                                            .path("/history")
                                            .queryParam("base", base)
                                            .queryParam("start_at", startDate)
                                            .queryParam("end_at", endDate)
                                            .build()
                                            .encode()
                                            .toUri();

        HistoricalExchangeRates historicalExchangeRates = restTemplate.getForObject(targetUrl, HistoricalExchangeRates.class);

        Map<String, BigDecimal> ratesStandardDeviation = ExchangeOperations
            .getStandardDeviationValues(Optional.of(historicalExchangeRates.getRates()));


        return CurrencyStandardDeviationsResponse.builder()
                                                 .start_at(startDate)
                                                 .end_at(endDate)
                                                 .base(base)
                                                 .ratesStandardDeviation(ratesStandardDeviation)
                                                 .build();
    }

}
