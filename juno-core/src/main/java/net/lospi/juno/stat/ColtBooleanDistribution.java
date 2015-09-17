/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.stat;

import cern.jet.random.Uniform;
import cern.jet.random.engine.RandomEngine;

public class ColtBooleanDistribution implements BooleanDistribution {
    private final cern.jet.random.Uniform uniform;
    private final CachedNaturalLogarithm naturalLogarithm;

    public ColtBooleanDistribution(RandomEngine engine, CachedNaturalLogarithm naturalLogarithm) {
        this.naturalLogarithm = naturalLogarithm;
        uniform = new Uniform(engine);
    }

    @Override
    public boolean nextWithLogProbability(double logAcceptanceProbability) {
        double auxiliary = uniform.nextDoubleFromTo(0, 1);
        double logAuxiliary = naturalLogarithm.apply(auxiliary);
        return logAuxiliary <= logAcceptanceProbability;
    }
}
