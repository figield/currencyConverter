package com.currency.adapters;

import com.currency.domain.service.RestOperation;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Value
@RequiredArgsConstructor
public class RestAdapter implements RestOperation {

    private RestTemplate restTemplate;

    @Nullable
    public <T> T getCurrencyData(URI url, Class<T> responseType) throws RestClientException {

        return restTemplate.getForObject(url, responseType);
    }

}
