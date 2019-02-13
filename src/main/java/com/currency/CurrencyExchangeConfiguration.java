package com.currency;

import com.currency.adapters.RestAdapter;
import com.currency.domain.service.ExchangeService;
import com.currency.domain.service.ExchangeComputationService;
import com.currency.domain.service.ClientCallService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@ComponentScan
@Configuration
public class CurrencyExchangeConfiguration {

    @Value("${exchange.vendor}")
    private String BASE_URL;

    @Bean
    public ClientCallService exchangeRestCalls() {
        return new ClientCallService(new RestAdapter(new RestTemplate()), BASE_URL);
    }

    @Bean
    public ExchangeComputationService exchangeService() {
        return new ExchangeComputationService();
    }

    @Bean
    public ExchangeService exchangeFacadeService() {
        return new ExchangeService();
    }


}
