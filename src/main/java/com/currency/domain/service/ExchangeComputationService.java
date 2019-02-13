package com.currency.domain.service;

import com.currency.domain.dto.*;
import com.currency.domain.service.utils.ExchangeCalculations;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExchangeComputationService {

    CurrencyConvertion convert(ExchangeRates exchangeRates, ExchangeParams exchangeParams) {
        return CurrencyConvertion.builder()
                                 .date(exchangeRates.getDate())
                                 .fromAmount(exchangeParams.getAmount())
                                 .fromCurrency(exchangeParams.getFrom())
                                 .toCurrency(exchangeParams.getTo())
                                 .toAmount(ExchangeCalculations.getMultiplicationValue(
                                     exchangeRates.getRates().get(exchangeParams.getTo()),
                                     exchangeParams.getAmount()))
                                 .build();
    }

    AverageExchangeRates average(HistoricalExchangeRates historicalExchangeRates, ExchangeParams exchangeParams) {
        return AverageExchangeRates.builder()
                                   .startDate(exchangeParams.getStartDate())
                                   .endDate(exchangeParams.getEndDate())
                                   .from(exchangeParams.getFrom())
                                   .to(exchangeParams.getTo())
                                   .average(ExchangeCalculations.getAverageForCurrencyValue(
                                       exchangeParams.getTo(),
                                       historicalExchangeRates.getRates()))
                                   .build();
    }

    CurrencyStandardDeviations standard_deviation(HistoricalExchangeRates historicalExchangeRates, ExchangeParams exchangeParams) {
        return CurrencyStandardDeviations.builder()
                                         .startDate(exchangeParams.getStartDate())
                                         .endDate(exchangeParams.getEndDate())
                                         .base(exchangeParams.getBase())
                                         .ratesStandardDeviation(ExchangeCalculations.getStandardDeviationValues(
                                             historicalExchangeRates.getRates()))
                                         .build();
    }

}
