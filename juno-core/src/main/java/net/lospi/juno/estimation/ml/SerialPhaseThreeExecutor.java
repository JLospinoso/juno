/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.calc.ParameterCovarianceEstimator;
import net.lospi.juno.estimation.elements.ConvergenceRatio;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.elements.ParameterCovariance;
import net.lospi.juno.estimation.sim.LikelihoodDerivativesSimulator;
import net.lospi.juno.model.Model;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class SerialPhaseThreeExecutor implements PhaseThreeExecutor {
    private static final Log LOGGER = LogFactory.getLog(SerialPhaseThreeExecutor.class);
    private ParameterCovarianceEstimator parameterCovarianceEstimator;
    private PhaseThreeResultsBuilder phaseThreeResultsBuilder;
    private final LikelihoodDerivativesSimulator likelihoodDerivativesSimulator;
    private Model model;
    private int phaseThreeSamples = 1000;
    private int samplingInterval = 0;
    private final ConvergenceRatioCalculator convergenceRatioCalculator;

    public SerialPhaseThreeExecutor(PhaseThreeResultsBuilder phaseThreeResultsBuilder, ParameterCovarianceEstimator parameterCovarianceEstimator,
                                    LikelihoodDerivativesSimulator likelihoodDerivativesSimulator, ConvergenceRatioCalculator convergenceRatioCalculator) {
        this.phaseThreeResultsBuilder = phaseThreeResultsBuilder;
        this.parameterCovarianceEstimator = parameterCovarianceEstimator;
        this.likelihoodDerivativesSimulator = likelihoodDerivativesSimulator;
        this.convergenceRatioCalculator = convergenceRatioCalculator;
    }

    @Override
    public PhaseThreeResults execute() {
        LOGGER.info(String.format("Executing phase three with %d samples.", phaseThreeSamples));
        likelihoodDerivativesSimulator.setSamplingInterval(samplingInterval);
        List<LikelihoodDerivatives> simulatedDerivatives = likelihoodDerivativesSimulator.simulateLikelihoodDerivatives(model, phaseThreeSamples);
        LOGGER.info("Sampling complete calculating parameterCovariance.");
        ParameterCovariance parameterCovariance = parameterCovarianceEstimator.calculate(simulatedDerivatives); //TODO: Get rid of this object
        ConvergenceRatio tRatios = convergenceRatioCalculator.calculate(simulatedDerivatives, parameterCovariance);
        return phaseThreeResultsBuilder.with()
                .covariance(parameterCovariance)
                .convergenceRatio(tRatios)
                .build();
    }

    @Override
    public void setPhaseThreeIterations(int phaseThreeSamples) {
        this.phaseThreeSamples = phaseThreeSamples;
    }

    @Override
    public void setSamplingInterval(int samplingInterval) {
        this.samplingInterval = samplingInterval;
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }
}