package com.currency.domain.service.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_UP;


// TODO: validate data
@Slf4j
public class ExchangeOperations {

    // To have a better control over the numbers
    static int PRECISION = 10;
    static MathContext MATH_CONTEXT = new MathContext(PRECISION, HALF_UP);
    static int CURRENCY_LIST_LIMIT = 30;


    public static BigDecimal getMultiplicationValue(BigDecimal rate, BigDecimal amount) {
        return rate.multiply(amount, MATH_CONTEXT);
    }

    public static BigDecimal getAverageForCurrencyValue(String toCurrency, Optional<Map<String, Map<String, BigDecimal>>> rates) {
        return rates.get()
                .values()
                .stream()
                .map(m -> m.get(toCurrency))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(rates.get().values().size()), MATH_CONTEXT);
    }

    public static Map<String, BigDecimal> getStandardDeviationValues(Optional<Map<String, Map<String, BigDecimal>>> rates) {

        Map<String, List<BigDecimal>> ratesAverages = collectAndNormalizeRatesValues(rates);

        Map<String, BigDecimal> averageValues = calculateAverageRateForEachCurrency(ratesAverages);

        return calculateStandardDeviationValuesForEachCurrency(ratesAverages, averageValues);

    }

    static Map<String, List<BigDecimal>> collectAndNormalizeRatesValues(Optional<Map<String, Map<String, BigDecimal>>> rates) {

        Map<String, List<BigDecimal>> ratesValues = new HashMap<>();

        rates.get()
                .values()
                .forEach(d -> d.keySet()
                        .forEach(k -> {
                            List<BigDecimal> l = ratesValues.getOrDefault(k, new ArrayList<>());
                            l.add(ONE.divide(d.get(k), MATH_CONTEXT));    // Normalize values
                            ratesValues.put(k, l);
                        }));

        return ratesValues;
    }


    static Map<String, BigDecimal> calculateAverageRateForEachCurrency(Map<String, List<BigDecimal>> ratesValues) {

        Map<String, BigDecimal> averageValues = new HashMap<>();

        ratesValues.keySet()
                .forEach(k -> averageValues.put(k,
                        ratesValues.get(k)
                                .stream()
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                .divide(BigDecimal.valueOf(ratesValues.get(k).size()), MATH_CONTEXT)
                ));

        return averageValues;
    }


    static Map<String, BigDecimal> calculateStandardDeviationValuesForEachCurrency(
            Map<String, List<BigDecimal>> ratesValues,
            Map<String, BigDecimal> averageValues) {

        Map<String, BigDecimal> standardDeviationValues = new HashMap<>();

        ratesValues.forEach((k, l) ->
                standardDeviationValues.put(
                        k,
                        calculateStandardDeviationValue(l, averageValues.get(k))));

        return standardDeviationValues.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(CURRENCY_LIST_LIMIT)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }


    static BigDecimal calculateStandardDeviationValue(List<BigDecimal> rateValues, BigDecimal averageValue) {

        BigDecimal ro2 = rateValues.stream()
                .map(v -> v.subtract(averageValue).pow(2))
                .reduce((v1, v2) -> v1.add(v2)).get()
                .divide(BigDecimal.valueOf(rateValues.size() - 1), MATH_CONTEXT);

        return new BigDecimal(Math.sqrt(ro2.doubleValue()), MATH_CONTEXT);
    }

}
