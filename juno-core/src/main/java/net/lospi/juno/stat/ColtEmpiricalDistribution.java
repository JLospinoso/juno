/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.stat;

import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;
import cern.jet.random.engine.RandomEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ColtEmpiricalDistribution<T> implements EmpiricalDistribution<T> {
    private EmpiricalWalker empirical;
    private List<T> elements;
    private double[] empiricalPdf;
    private RandomEngine engine;

    public ColtEmpiricalDistribution(Map<T, Double> densityFunction, RandomEngine engine){
        setDensityFunction(densityFunction);
        setRandomEngine(engine);
        setEmpirical();
    }

    @Override
    public T next(){
        int indexOfNext = empirical.nextInt();
        return elements.get(indexOfNext);
    }

    private void setDensityFunction(Map<T, Double> densityFunction){
        int numberOfElements = densityFunction.size();
        elements = new ArrayList<T>(numberOfElements);
        empiricalPdf = new double[numberOfElements];
        int entryIndex = 0;
        for(Map.Entry<T, Double> entry : densityFunction.entrySet()){
            empiricalPdf[entryIndex] = entry.getValue();
            elements.add(entry.getKey());
            entryIndex++;
        }
    }

    private void setRandomEngine(RandomEngine engine){
        this.engine = engine;
    }

    private void setEmpirical() {
        empirical = new EmpiricalWalker(empiricalPdf, Empirical.LINEAR_INTERPOLATION, engine);
    }
}
