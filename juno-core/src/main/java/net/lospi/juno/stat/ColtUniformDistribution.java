/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.stat;

import cern.jet.random.Uniform;
import cern.jet.random.engine.RandomEngine;

import java.util.List;

public class ColtUniformDistribution implements UniformDistribution {
    private final cern.jet.random.Uniform uniform;

    public ColtUniformDistribution(RandomEngine engine) {
        uniform = new Uniform(engine);
    }

    @Override
    public double nextContinuous(double lower, double upper){
        return uniform.nextDoubleFromTo(lower, upper);
    }

    @Override
    public int next(int lower, int upper) {
        return uniform.nextIntFromTo(lower, upper);
    }

    @Override
    public <T> T next(List<T> items) {
        int index = next(0, items.size()-1);
        return items.get(index);
    }

    @Override
    public <T> T nextNotEqualTo(T itemToExclude, List<T> items) {
        if(items.size() == 1){
            throw new IllegalArgumentException("List must contain more than one element.");
        }
        T result;
        do{
            result = next(items);
        } while(result.equals(itemToExclude));
        return result;
    }
}
