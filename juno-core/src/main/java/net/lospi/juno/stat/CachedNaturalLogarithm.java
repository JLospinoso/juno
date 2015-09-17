/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.stat;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CachedNaturalLogarithm implements Function<Double, Double> {
    private CapacatorStore<Double, Double> doubleCache;
    private CapacatorStore<Integer, Double> intCache;

    public CachedNaturalLogarithm(){
        doubleCache = new CapacatorStore<Double, Double>();
        intCache = new CapacatorStore<Integer, Double>();
    }

    @Override
    public Double apply(Double value) {
        Double result = doubleCache.get(value);
        if(result == null){
            result = Math.log(value);
            doubleCache.put(value, result);
        }
        return result;
    }

    public double apply(int value) {
        Double result = intCache.get(value);
        if(result == null){
            result = Math.log(value);
            intCache.put(value, result);
        }
        return result;
    }
}
