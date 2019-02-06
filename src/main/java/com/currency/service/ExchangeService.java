package com.currency.service;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;

import com.currency.dto.AverageExchangeRatesResponse;
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

        ExchangeRates exchangeRates = restTemplate.getForObject(targetUrl, ExchangeRates.class);

        return exchangeRates;
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

        Map<String, Map<String, BigDecimal>> rates = historicalExchangeRates.getRates();

        BigDecimal average = getAverageCurrencyValue(toCurrency, rates);

        return AverageExchangeRatesResponse
            .builder()
            .start_at(startDate)
            .end_at(endDate)
            .base(fromCurrency)
            .average(average).build();

    }

    private BigDecimal getAverageCurrencyValue(String toCurrency, Map<String, Map<String, BigDecimal>> rates) {
        return rates.values()
                                      .stream()
                                      .map(m -> m.get(toCurrency))
                                      .reduce(BigDecimal.ZERO, BigDecimal::add)
                                      .divide(BigDecimal.valueOf(rates.values().size()));
    }



}
