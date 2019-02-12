package com.currency.domain.service.utils;

import java.net.URI;

import com.currency.domain.dto.ExchangeRates;
import com.currency.domain.dto.HistoricalExchangeRates;
import com.currency.domain.service.RestOperation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@AllArgsConstructor
public class ExchangeRestCalls {

    private RestOperation restOperation;
    private String BASE_URL;

    public ExchangeRates getLatestExchangeRate(String base) {

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                                            .path("/latest")
                                            .queryParam("base", base)
                                            .build()
                                            .encode()
                                            .toUri();

        return restOperation.getCurrencyData(targetUrl, ExchangeRates.class);
    }


    public HistoricalExchangeRates getHistoricalExchangeRates(String fromCurrency, String startDate, String endDate) {
        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                                            .path("/history")
                                            .queryParam("base", fromCurrency)
                                            .queryParam("start_at", startDate)
                                            .queryParam("end_at", endDate)
                                            .build()
                                            .encode()
                                            .toUri();

        return restOperation.getCurrencyData(targetUrl, HistoricalExchangeRates.class);
    }
}
