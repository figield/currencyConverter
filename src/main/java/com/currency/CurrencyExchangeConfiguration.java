package com.currency;

import com.currency.domain.ExchangeService;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@ComponentScan
@Configuration
public class CurrencyExchangeConfiguration {

    @Bean
    public ExchangeService exchangeService() {
        RestTemplate restTemplate = new RestTemplate();
        String BASE_URL = "https://api.exchangeratesapi.io";
        return new ExchangeService(restTemplate, BASE_URL);
    }

}