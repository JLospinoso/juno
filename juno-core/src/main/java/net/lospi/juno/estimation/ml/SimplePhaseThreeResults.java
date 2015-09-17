/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.elements.ParameterCovariance;
import net.lospi.juno.estimation.elements.ConvergenceRatio;

public class SimplePhaseThreeResults implements PhaseThreeResults {
    private final String status;
    private final boolean successful;
    private final ParameterCovariance parameterCovariance;
    private final ConvergenceRatio tRatios;

    public SimplePhaseThreeResults(String status, boolean successful, ParameterCovariance parameterCovariance,
                                   ConvergenceRatio tRatios) {
        this.status = status;
        this.successful = successful;
        this.parameterCovariance = parameterCovariance;
        this.tRatios = tRatios;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public boolean isSuccessful() {
        return successful;
    }

    @Override
    public ParameterCovariance getParameterCovariance() {
        return parameterCovariance;
    }

    @Override
    public ConvergenceRatio getConvergenceRatio() {
        return tRatios;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(String.format("%n  Successful: %b%n", successful));
        builder.append(String.format("  Status: %s%n", status));
        builder.append(String.format("%s%n", parameterCovariance.toString()));
        builder.append(String.format("%s", tRatios.toString()));
        return builder.toString();
    }
}
