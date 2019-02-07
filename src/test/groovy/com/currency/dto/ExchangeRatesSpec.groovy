package com.currency.dto

import spock.lang.Specification

class ExchangeRatesSpec extends Specification {


    def "Should build ExchangeRates"() {
        given:
            String base = "USD"
            String date = "2019-02-02"
            Map<String, BigDecimal> rates = new HashMap<>().put("PLN", 3.7)
        when:
            ExchangeRates exchangeRates = new ExchangeRates()
            exchangeRates.setBase(base)
            exchangeRates.setDate(date)
            exchangeRates.setRates(rates)
        then:
            exchangeRates.base == "USD"
            exchangeRates.date == "2019-02-02"
            exchangeRates.rates == rates

    }

}
