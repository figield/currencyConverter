package com.currency.domain.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import com.currency.domain.dto.*;
import com.currency.domain.service.utils.DateOperations;
import com.currency.domain.service.utils.ExchangeCalculations;
import com.currency.domain.service.utils.ExchangeRestCalls;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@AllArgsConstructor
public class ExchangeService {

    ExchangeRestCalls exchangeRestCalls;

    public ExchangeRates latest(String base) {
        return exchangeRestCalls.getLatestExchangeRate(base);
    }

    public CurrencyConvertion convert(BigDecimal fromAmount, String fromCurrency, String toCurrency) {

        if (fromCurrency.equals(toCurrency)) {
            return CurrencyConvertion.builder()
                                     .date(DateOperations.getYesterdayDateString())
                                     .fromAmount(fromAmount)
                                     .fromCurrency(fromCurrency)
                                     .toCurrency(toCurrency)
                                     .toAmount(ExchangeCalculations.getMultiplicationValue(BigDecimal.valueOf(1), fromAmount))
                                     .build();
        }

        ExchangeRates exchangeRates = exchangeRestCalls.getLatestExchangeRate(fromCurrency);
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


    public AverageExchangeRates average(String fromCurrency, String toCurrency, String startDate, String endDate) {

        if (fromCurrency.equals(toCurrency)) {
            return AverageExchangeRates.builder()
                                       .startDate(startDate)
                                       .endDate(endDate)
                                       .from(fromCurrency)
                                       .to(toCurrency)
                                       .average(BigDecimal.valueOf(1))
                                       .build();
        }

        BigDecimal average = ExchangeCalculations.getAverageForCurrencyValue(toCurrency,
            Optional.of(exchangeRestCalls.getHistoricalExchangeRates(fromCurrency, startDate, endDate).getRates()));

        return AverageExchangeRates.builder()
                                   .startDate(startDate)
                                   .endDate(endDate)
                                   .from(fromCurrency)
                                   .to(toCurrency)
                                   .average(average)
                                   .build();
    }


    public CurrencyStandardDeviations standard_deviation(String base, String startDate, String endDate) {

        Map<String, BigDecimal> ratesStandardDeviation = ExchangeCalculations.getStandardDeviationValues(
            Optional.of(exchangeRestCalls.getHistoricalExchangeRates(base, startDate, endDate).getRates()));

        return CurrencyStandardDeviations.builder()
                                         .startDate(startDate)
                                         .endDate(endDate)
                                         .base(base)
                                         .ratesStandardDeviation(ratesStandardDeviation)
                                         .build();
    }

}
