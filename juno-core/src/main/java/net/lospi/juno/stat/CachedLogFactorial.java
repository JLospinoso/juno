package net.lospi.juno.stat;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

public class CachedLogFactorial implements Function<Integer, Double> {
    private final CapacatorStore<Integer, Double> logFactorialCache;

    public CachedLogFactorial() {
        logFactorialCache = new CapacatorStore<Integer, Double>();
    }

    @Override
    public Double apply(Integer value) {
        Double result = logFactorialCache.get(value);
        if(result == null){
            result = doLogFactorial(value);
            logFactorialCache.put(value, result);
        }
        return result;
    }

    private double doLogFactorial(int chainSize) {
        double result = 0;
        for (int i = 1; i <= chainSize; i++) {
            result += i;
        }
        return result;
    }
}
