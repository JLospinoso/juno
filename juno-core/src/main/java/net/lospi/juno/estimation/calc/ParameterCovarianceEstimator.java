/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.calc;

import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.elements.ParameterCovariance;

import java.util.List;

public interface ParameterCovarianceEstimator {
    ParameterCovariance calculate(List<LikelihoodDerivatives> simulatedDerivatives);
}
