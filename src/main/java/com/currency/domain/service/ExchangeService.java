package com.currency.domain.service;

import com.currency.domain.dto.ExchangeParams;
import com.currency.domain.dto.AverageExchangeRates;
import com.currency.domain.dto.CurrencyConvertion;
import com.currency.domain.dto.CurrencyStandardDeviations;
import com.currency.domain.dto.ExchangeRates;
import com.currency.domain.service.utils.DateOperations;
import com.currency.domain.service.utils.ExchangeCalculations;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import static java.math.BigDecimal.ONE;

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

    public CurrencyStandardDeviations standard_deviation(ExchangeParams exchangeParams) {
        return exchangeComputationService.standard_deviation(
            clientCallService.getHistoricalExchangeRates(exchangeParams),
            exchangeParams);
    }

    public CurrencyConvertion convert(ExchangeParams exchangeParams) {

        if (exchangeParams.sameCurrency()) {
            return getShortcutForCurrencyConvertion(exchangeParams);
        }

        return exchangeComputationService.convert(
            clientCallService.getLatestExchangeRate(exchangeParams.getFrom()),
            exchangeParams);
    }

    public AverageExchangeRates average(ExchangeParams exchangeParams) {

        if (exchangeParams.sameCurrency()) {
            return getShortcutForAverageExchangeRates(exchangeParams);
        }

        return exchangeComputationService.average(
            clientCallService.getHistoricalExchangeRates(exchangeParams),
            exchangeParams);
    }

    private AverageExchangeRates getShortcutForAverageExchangeRates(ExchangeParams exchangeParams) {
        return AverageExchangeRates.builder()
                                   .startDate(exchangeParams.getStartDate())
                                   .endDate(exchangeParams.getEndDate())
                                   .from(exchangeParams.getFrom())
                                   .to(exchangeParams.getTo())
                                   .average(ONE)
                                   .build();
    }

    private CurrencyConvertion getShortcutForCurrencyConvertion(ExchangeParams exchangeParams) {
        return CurrencyConvertion.builder()
                                 .date(DateOperations.getYesterdayDateString())
                                 .fromAmount(exchangeParams.getAmount())
                                 .fromCurrency(exchangeParams.getFrom())
                                 .toCurrency(exchangeParams.getTo())
                                 .toAmount(
                                     ExchangeCalculations.getMultiplicationValue(ONE,
                                         exchangeParams.getAmount()))
                                 .build();
    }
}
