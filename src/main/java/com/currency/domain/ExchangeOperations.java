package com.currency.domain;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static java.math.RoundingMode.HALF_DOWN;

class ExchangeOperations {

    static BigDecimal getAverageCurrencyValue(String toCurrency, Optional<Map<String, Map<String, BigDecimal>>> rates) {
        return rates.get().values()
                    .stream()
                    .map(m -> m.get(toCurrency))
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(rates.get().values().size()), HALF_DOWN);
    }
}
