package com.currency.domain.service;

import java.math.BigDecimal;

import com.currency.domain.dto.AverageExchangeRates;
import com.currency.domain.dto.CurrencyConvertion;
import com.currency.domain.dto.CurrencyStandardDeviations;
import com.currency.domain.dto.ExchangeRates;
import com.currency.domain.dto.HistoricalExchangeRates;
import com.currency.domain.service.utils.DateOperations;
import com.currency.domain.service.utils.ExchangeCalculations;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
@AllArgsConstructor
public class ExchangeService {

    @Autowired
    ClientCallService clientCallService;

    @Autowired
    ExchangeComputationService exchangeComputationService;

    public ExchangeRates latest(String base) {
        return clientCallService.getLatestExchangeRate(base);
    }

    public CurrencyStandardDeviations standard_deviation(String base, String startDate, String endDate) {
        HistoricalExchangeRates historicalExchangeRates = clientCallService.getHistoricalExchangeRates(base, startDate, endDate);

        return exchangeComputationService.standard_deviation(historicalExchangeRates, base, startDate, endDate);
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

        ExchangeRates exchangeRates = clientCallService.getLatestExchangeRate(fromCurrency);

        return exchangeComputationService.convert(exchangeRates, fromAmount, fromCurrency, toCurrency);

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

        HistoricalExchangeRates historicalExchangeRates = clientCallService.getHistoricalExchangeRates(fromCurrency, startDate, endDate);

        return exchangeComputationService.average(historicalExchangeRates, fromCurrency,  toCurrency,  startDate,  endDate);

    }

}
