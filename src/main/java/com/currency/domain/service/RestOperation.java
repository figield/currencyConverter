package com.currency.domain.service;

import org.springframework.lang.Nullable;
import org.springframework.web.client.RestClientException;

import java.net.URI;

public interface RestOperation {

    /**
     * Retrieve a representation by doing a GET on the URL .
     * The response (if any) is converted and returned.
     * @param url the URL
     * @param responseType the type of the return value
     * @return the converted object
     */
    @Nullable
    <T> T getForObject(URI url, Class<T> responseType) throws RestClientException;

}
