/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.MinistepChainCalculator;
import net.lospi.juno.stat.CachedNaturalLogarithm;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class CcpDeletionProposalRatioCalculatorTest {
    private CachedNaturalLogarithm naturalLogarithm;
    private CcpDeletionMaximumLengthCalculator ccpDeletionMaximumLengthCalculator;
    private int min;
    private int max;
    private double insertionProbability;
    private CcpDeletionModification modification;
    private Chain state;
    private double expected;
    private int adjustedMax;
    private int ministeps;
    private int actorAspects;
    private int ccpCount;
    private MinistepChainCalculator ministepChainCalculator;

    @BeforeMethod
    public void setUp() throws Exception {
        naturalLogarithm = mock(CachedNaturalLogarithm.class);
        ccpDeletionMaximumLengthCalculator = mock(CcpDeletionMaximumLengthCalculator.class);
        modification = mock(CcpDeletionModification.class);
        state = mock(Chain.class);
        ministepChainCalculator = mock(MinistepChainCalculator.class);
        min = 2;
        max = 40;
        adjustedMax = 27;
        insertionProbability = 0.7;
        ministeps = 60;
        actorAspects = 100;
        ccpCount = 15;

        when(modification.getStart()).thenReturn(10);
        when(ccpDeletionMaximumLengthCalculator.calculate(max, 10, state)).thenReturn(adjustedMax);
        when(state.getSize()).thenReturn(ministeps);
        when(state.getActorAspectCount()).thenReturn(actorAspects);
        when(ministepChainCalculator.getConsecutivelyCancellingPairsCount(state, min, max)).thenReturn(ccpCount);
        when(naturalLogarithm.apply(insertionProbability)).thenReturn(1D);
        when(naturalLogarithm.apply(actorAspects - 1)).thenReturn(2D);
        when(naturalLogarithm.apply(ministeps - min)).thenReturn(3D);
        when(naturalLogarithm.apply(adjustedMax - min + 1)).thenReturn(4D);
        when(naturalLogarithm.apply(1 - insertionProbability)).thenReturn(5D);
        when(naturalLogarithm.apply(ccpCount)).thenReturn(6D);

        expected = 1 - 4 - 3 - 4 - 10 + 6;
    }

    public void testCalculateLogProposalRatio() throws Exception {
        CcpDeletionProposalRatioCalculator calculator = new CcpDeletionProposalRatioCalculator(naturalLogarithm, ccpDeletionMaximumLengthCalculator, ministepChainCalculator, min, max);
        double result = calculator.calculateLogProposalRatio(insertionProbability, modification, state);
        assertThat(result).isEqualTo(expected);
    }
}
