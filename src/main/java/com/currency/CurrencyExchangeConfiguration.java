package com.currency;

import com.currency.adapters.RestAdapter;
import com.currency.domain.service.ExchangeService;
import com.currency.domain.service.utils.ExchangeRestCalls;
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
        return new ExchangeService(exchangeRestCalls());
    }

    private ExchangeRestCalls exchangeRestCalls() {
        String BASE_URL = "https://api.exchangeratesapi.io";
        return new ExchangeRestCalls(new RestAdapter(new RestTemplate()), BASE_URL);
    }

}
