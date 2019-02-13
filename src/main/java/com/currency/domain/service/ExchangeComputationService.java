package com.currency.domain.service;

import java.math.BigDecimal;
import java.util.Map;

import com.currency.domain.dto.*;
import com.currency.domain.service.utils.ExchangeCalculations;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExchangeComputationService {

    CurrencyConvertion convert(ExchangeRates exchangeRates, BigDecimal fromAmount, String fromCurrency, String toCurrency) {

        BigDecimal rateValue = exchangeRates.getRates().get(toCurrency);
        String date = exchangeRates.getDate();
        BigDecimal exchangeValue = ExchangeCalculations.getMultiplicationValue(rateValue, fromAmount);

        return CurrencyConvertion.builder()
                                 .date(date)
                                 .fromAmount(fromAmount)
                                 .fromCurrency(fromCurrency)
                                 .toCurrency(toCurrency)
                                 .toAmount(exchangeValue)
                                 .build();
    }

    AverageExchangeRates average(HistoricalExchangeRates historicalExchangeRates, String fromCurrency, String toCurrency, String startDate,
        String endDate) {

        BigDecimal average = ExchangeCalculations.getAverageForCurrencyValue(toCurrency,
            historicalExchangeRates.getRates());

        return AverageExchangeRates.builder()
                                   .startDate(startDate)
                                   .endDate(endDate)
                                   .from(fromCurrency)
                                   .to(toCurrency)
                                   .average(average)
                                   .build();
    }

    CurrencyStandardDeviations standard_deviation(HistoricalExchangeRates historicalExchangeRates, String base, String startDate, String endDate) {

        Map<String, BigDecimal> ratesStandardDeviation = ExchangeCalculations.getStandardDeviationValues(
            historicalExchangeRates.getRates());

        return CurrencyStandardDeviations.builder()
                                         .startDate(startDate)
                                         .endDate(endDate)
                                         .base(base)
                                         .ratesStandardDeviation(ratesStandardDeviation)
                                         .build();
    }

}
