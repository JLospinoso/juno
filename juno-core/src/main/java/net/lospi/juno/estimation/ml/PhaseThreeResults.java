/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.elements.ConvergenceRatio;
import net.lospi.juno.estimation.elements.ParameterCovariance;

public interface PhaseThreeResults {
    String getStatus();
    boolean isSuccessful();
    ParameterCovariance getParameterCovariance();

    ConvergenceRatio getConvergenceRatio();
}
