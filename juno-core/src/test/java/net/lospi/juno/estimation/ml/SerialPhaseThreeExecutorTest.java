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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class SerialPhaseThreeExecutorTest {
    private PhaseThreeResultsBuilder phaseThreeResultsBuilder;
    private ParameterCovarianceEstimator parameterCovarianceEstimator;
    private Model model;
    private PhaseThreeResults phaseThreeResults;
    private ParameterCovariance covariance;
    private PhaseThreeResultsBuilder.Stub stub;
    private LikelihoodDerivativesSimulator likelihoodDerivativesSimulator;
    private int phaseThreeIterations, samplingInterval;
    private List<LikelihoodDerivatives> derivatives;
    private ConvergenceRatioCalculator tRatioCalculator;
    private ConvergenceRatio tRatios;

    @BeforeMethod
    public void setUp(){
        stub = mock(PhaseThreeResultsBuilder.Stub.class);
        phaseThreeResultsBuilder = mock(PhaseThreeResultsBuilder.class);
        parameterCovarianceEstimator = mock(ParameterCovarianceEstimator.class);
        model = mock(Model.class);
        phaseThreeResults = mock(PhaseThreeResults.class);
        phaseThreeIterations = 3;
        samplingInterval = 10;
        covariance = mock(ParameterCovariance.class);
        likelihoodDerivativesSimulator = mock(LikelihoodDerivativesSimulator.class);
        derivatives = mock(List.class);
        tRatios = mock(ConvergenceRatio.class);
        tRatioCalculator = mock(ConvergenceRatioCalculator.class);

        when(likelihoodDerivativesSimulator.simulateLikelihoodDerivatives(model, phaseThreeIterations)).thenReturn(derivatives);
        when(parameterCovarianceEstimator.calculate(derivatives)).thenReturn(covariance);
        when(tRatioCalculator.calculate(derivatives, covariance)).thenReturn(tRatios);
        when(phaseThreeResultsBuilder.with()).thenReturn(stub);
        when(stub.covariance(covariance)).thenReturn(stub);
        when(stub.convergenceRatio(tRatios)).thenReturn(stub);
        when(stub.build()).thenReturn(phaseThreeResults);
    }

    public void callsDependenciesCorrectly() throws Exception {
        SerialPhaseThreeExecutor executor = new SerialPhaseThreeExecutor(phaseThreeResultsBuilder, parameterCovarianceEstimator,
                likelihoodDerivativesSimulator, tRatioCalculator);
        executor.setModel(model);
        executor.setPhaseThreeIterations(phaseThreeIterations);
        executor.setSamplingInterval(samplingInterval);

        PhaseThreeResults result = executor.execute();
        assertThat(result).isEqualTo(phaseThreeResults);

        verify(likelihoodDerivativesSimulator).setSamplingInterval(samplingInterval);
        verify(stub).covariance(covariance);
        verify(stub).convergenceRatio(tRatios);
    }
}
