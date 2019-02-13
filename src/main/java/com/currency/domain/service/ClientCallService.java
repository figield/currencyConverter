package com.currency.domain.service;

import java.net.URI;

import com.currency.domain.dto.ExchangeParams;
import com.currency.domain.dto.ExchangeRates;
import com.currency.domain.dto.HistoricalExchangeRates;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@AllArgsConstructor
public class ClientCallService {

    private RestOperation restOperation;
    private String BASE_URL;

    ExchangeRates getLatestExchangeRate(String base) {

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                                            .path("/latest")
                                            .queryParam("base", base)
                                            .build()
                                            .encode()
                                            .toUri();

        return restOperation.getCurrencyData(targetUrl, ExchangeRates.class);
    }


    HistoricalExchangeRates getHistoricalExchangeRates(ExchangeParams exchangeParams) {
        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                                            .path("/history")
                                            .queryParam("base", exchangeParams.getBase())
                                            .queryParam("start_at", exchangeParams.getStartDate())
                                            .queryParam("end_at", exchangeParams.getEndDate())
                                            .build()
                                            .encode()
                                            .toUri();

        return restOperation.getCurrencyData(targetUrl, HistoricalExchangeRates.class);
    }
}
