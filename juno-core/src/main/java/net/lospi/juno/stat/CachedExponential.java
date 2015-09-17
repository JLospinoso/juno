/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.stat;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

public class CachedExponential implements Function<Double, Double> {
    private CapacatorStore<Double, Double> lnCache;

    public CachedExponential(){
        lnCache = new CapacatorStore<Double, Double>();
    }

    public Double apply(Double value) {
        Double result = lnCache.get(value);
        if(result == null){
            result = Math.exp(value);
            lnCache.put(value, result);
        }
        return result;
    }
}
