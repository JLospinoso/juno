/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.ml.*;
import net.lospi.juno.model.Model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class SerialMaximumLikelihoodEstimatorTest {
    private MaximumLikelihoodResultsBuilder maximumLikelihoodResultsBuilder;
    private PhaseOneExecutor phaseOneExecutor;
    private PhaseTwoExecutor phaseTwoExecutor;
    private PhaseThreeExecutor phaseThreeExecutor;
    private PhaseOneResults phaseOneResults;
    private PhaseTwoResults phaseTwoResults;
    private PhaseThreeResults phaseThreeResults;
    private MaximumLikelihoodResults expectedResults;
    private String errorMessage = "ERROR MESSAGE";
    private Model model;
    private MaximumLikelihoodResultsBuilder.Stub stub;
    private Chain initialChain;
    private RobbinsMonroPlan plan;
    private WeightMatrix weightMatrix;

    @BeforeMethod
    public void setUp() throws Exception {
        maximumLikelihoodResultsBuilder = mock(MaximumLikelihoodResultsBuilder.class);
        phaseOneExecutor = mock(PhaseOneExecutor.class);
        phaseTwoExecutor = mock(PhaseTwoExecutor.class);
        phaseThreeExecutor = mock(PhaseThreeExecutor.class);
        phaseOneResults = mock(PhaseOneResults.class);
        phaseTwoResults = mock(PhaseTwoResults.class);
        phaseThreeResults = mock(PhaseThreeResults.class);
        expectedResults = mock(SimpleMaximumLikelihoodResults.class);
        model = mock(Model.class);
        stub = mock(MaximumLikelihoodResultsBuilder.Stub.class);
        initialChain = mock(Chain.class);
        plan = mock(RobbinsMonroPlan.class);
        weightMatrix = mock(WeightMatrix.class);

        when(phaseOneExecutor.execute(model, initialChain)).thenReturn(phaseOneResults);
        when(phaseOneResults.getWeightMatrix()).thenReturn(weightMatrix);
        when(phaseTwoExecutor.execute(model, weightMatrix, plan)).thenReturn(phaseTwoResults);
        when(phaseThreeExecutor.execute()).thenReturn(phaseThreeResults);
        when(phaseTwoResults.getEstimatedModel()).thenReturn(model);

        when(maximumLikelihoodResultsBuilder.with()).thenReturn(stub);
        when(stub.phaseOneResults(phaseOneResults)).thenReturn(stub);
        when(stub.phaseTwoResults(phaseTwoResults)).thenReturn(stub);
        when(stub.phaseThreeResults(phaseThreeResults)).thenReturn(stub);
        when(stub.build()).thenReturn(expectedResults);
    }

    public void estimate() throws Exception {
        SerialMaximumLikelihoodEstimator estimator = new SerialMaximumLikelihoodEstimator(maximumLikelihoodResultsBuilder,
                phaseOneExecutor, phaseTwoExecutor, phaseThreeExecutor);
        when(phaseOneResults.isSuccessful()).thenReturn(true);
        when(phaseTwoResults.isSuccessful()).thenReturn(true);
        when(phaseThreeResults.isSuccessful()).thenReturn(true);
        MaximumLikelihoodResults result = estimator.estimate(model, initialChain, plan);
        assertThat(result).isEqualTo(expectedResults);
        verify(stub).phaseOneResults(phaseOneResults);
        verify(stub).phaseTwoResults(phaseTwoResults);
        verify(stub).phaseThreeResults(phaseThreeResults);
    }

    public void setSamplingInterval() throws Exception {
        int samplingInterval = 100;

        SerialMaximumLikelihoodEstimator estimator = new SerialMaximumLikelihoodEstimator(maximumLikelihoodResultsBuilder,
                phaseOneExecutor, phaseTwoExecutor, phaseThreeExecutor);
        estimator.setSamplingInterval(samplingInterval);

        verify(phaseOneExecutor).setSamplingInterval(samplingInterval);
        verify(phaseTwoExecutor).setSamplingInterval(samplingInterval);
        verify(phaseThreeExecutor).setSamplingInterval(samplingInterval);
    }

    public void setPhaseOneBurnIn() throws Exception {
        int expected = 100;

        SerialMaximumLikelihoodEstimator estimator = new SerialMaximumLikelihoodEstimator(maximumLikelihoodResultsBuilder,
                phaseOneExecutor, phaseTwoExecutor, phaseThreeExecutor);
        estimator.setPhaseOneBurnIn(expected);

        verify(phaseOneExecutor).setBurnIn(expected);
    }

    public void setPhaseOneSample() throws Exception {
        int expected = 100;

        SerialMaximumLikelihoodEstimator estimator = new SerialMaximumLikelihoodEstimator(maximumLikelihoodResultsBuilder,
                phaseOneExecutor, phaseTwoExecutor, phaseThreeExecutor);
        estimator.setPhaseOneSample(expected);

        verify(phaseOneExecutor).setSamples(expected);
    }

    public void setDrawsPerPhaseTwoSample() throws Exception {
        int expected = 100;

        SerialMaximumLikelihoodEstimator estimator = new SerialMaximumLikelihoodEstimator(maximumLikelihoodResultsBuilder,
                phaseOneExecutor, phaseTwoExecutor, phaseThreeExecutor);
        estimator.setDrawsPerPhaseTwoSample(expected);

        verify(phaseTwoExecutor).setDrawsPerSample(expected);
    }

    public void throwPhaseOneFailure() throws Exception {
        SerialMaximumLikelihoodEstimator estimator = new SerialMaximumLikelihoodEstimator(maximumLikelihoodResultsBuilder,
                phaseOneExecutor, phaseTwoExecutor, phaseThreeExecutor);
        when(phaseOneResults.isSuccessful()).thenReturn(false);
        when(phaseOneResults.getStatus()).thenReturn(errorMessage);
        estimator.estimate(model, initialChain, plan);
    }

    public void throwPhaseTwoFailure() throws Exception {
        SerialMaximumLikelihoodEstimator estimator = new SerialMaximumLikelihoodEstimator(maximumLikelihoodResultsBuilder,
                phaseOneExecutor, phaseTwoExecutor, phaseThreeExecutor);
        when(phaseOneResults.isSuccessful()).thenReturn(true);
        when(phaseTwoResults.isSuccessful()).thenReturn(false);
        when(phaseTwoResults.getStatus()).thenReturn(errorMessage);
        estimator.estimate(model, initialChain, plan);
    }

    public void throwPhaseThreeFailure() throws Exception {
        SerialMaximumLikelihoodEstimator estimator = new SerialMaximumLikelihoodEstimator(maximumLikelihoodResultsBuilder,
                phaseOneExecutor, phaseTwoExecutor, phaseThreeExecutor);
        when(phaseOneResults.isSuccessful()).thenReturn(true);
        when(phaseTwoResults.isSuccessful()).thenReturn(true);
        when(phaseThreeResults.isSuccessful()).thenReturn(false);
        when(phaseThreeResults.getStatus()).thenReturn(errorMessage);
        estimator.estimate(model, initialChain, plan);
    }
}
