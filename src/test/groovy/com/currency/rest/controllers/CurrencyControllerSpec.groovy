package com.currency.rest.controllers


import com.currency.domain.dto.CurrencyConvertion
import com.currency.domain.dto.ExchangeRates
import com.currency.domain.service.ExchangeComputationService
import com.currency.domain.service.ClientCallService
import com.currency.domain.service.ExchangeService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Unroll
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [CurrencyController])
class CurrencyControllerSpec extends Specification {

    @Autowired
    protected MockMvc mvc

    @Autowired
    ExchangeComputationService exchangeComputationService

    @Autowired
    ClientCallService exchangeRestCalls

    @Autowired
    ExchangeService exchangeService

    @Autowired
    ObjectMapper objectMapper


    def "should get latest currency result"() {
        given:
            Map<String, BigDecimal> rates = new HashMap<String, BigDecimal>()
            rates.put("PLN", 4.3064)
            rates.put("USD", 1.1346)
            ExchangeRates exchangeRates = new ExchangeRates()
            exchangeRates.setBase('EUR')
            exchangeRates.setDate('2019-02-08')
            exchangeRates.setRates(rates)
        and:
            exchangeService.latest('EUR') >> exchangeRates

        when:
            def results = mvc.perform(get('/latest'))

        then:
            results.andExpect(status().isOk())

        and:
            results.andExpect(jsonPath('$.base').value('EUR'))
            results.andExpect(jsonPath('$.rates.PLN').value(4.3064))
            results.andExpect(jsonPath('$.rates.USD').value(1.1346))
            results.andExpect(jsonPath('$.date').value('2019-02-08'))
    }


    @Unroll
    def "should convert #fromAmount #from to #to"() {
        given:
            CurrencyConvertion currencyConvertion =
                    new CurrencyConvertion(
                            date,
                            fromAmount,
                            from,
                            to,
                            toAmount)
        and:
            exchangeService.convert(_, _, _) >> currencyConvertion

        when:
            def results = mvc.perform(get('/convert')
                    .param("fromamount", String.valueOf(fromAmount))
                    .param("to", to)
                    .param("from", from))

        then:
            results.andExpect(status().isOk())

        and:
            results.andExpect(jsonPath('$.date').value(date))
            results.andExpect(jsonPath('$.fromAmount').value(fromAmount))
            results.andExpect(jsonPath('$.from').value(from))
            results.andExpect(jsonPath('$.toAmount').value(toAmount))
            results.andExpect(jsonPath('$.to').value(to))

        where:
            date         | fromAmount | from  | toAmount | to
            '2019-02-08' | 1.3        | 'PLN' | 3.0      | "USD"
            '2019-02-09' | 2.2        | 'EUR' | 4.0      | "USD"
            '2019-02-10' | 3.1        | 'PLN' | 1.0      | "EUR"
    }


    @TestConfiguration
    static class StubConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        ExchangeComputationService exchangeComputationService() {
            return detachedMockFactory.Stub(ExchangeComputationService)
        }

        @Bean
         ClientCallService clientCallService() {
            return detachedMockFactory.Stub(ClientCallService)
        }

        @Bean
        ExchangeService exchangeService() {
            return detachedMockFactory.Stub(ExchangeService)
        }

    }
}
