/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.MinistepChainCalculator;
import net.lospi.juno.stat.CachedNaturalLogarithm;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class DiagonalNetworkMinistepDeletionProposalRatioCalculatorTest {
    private CachedNaturalLogarithm logCalc;
    private double insertionProbability = .789;
    private Chain state;
    private double expectedRatio;
    private int diagonalLinksCount = 5;
    private int ministepCount = 10;
    private int actorAspects = 20;
    private DiagonalNetworkMinistepDeletionModification modification;
    private MinistepChainCalculator ministepChainCalculator;

    @BeforeMethod
    public void setUp(){
        ministepChainCalculator = mock(MinistepChainCalculator.class);
        state = mock(Chain.class);
        logCalc = mock(CachedNaturalLogarithm.class);
        modification = mock(DiagonalNetworkMinistepDeletionModification.class);
        when(ministepChainCalculator.getDiagonalLinksCount(state)).thenReturn(diagonalLinksCount);
        when(state.getSize()).thenReturn(ministepCount);
        when(state.getActorAspectCount()).thenReturn(actorAspects);
        when(logCalc.apply(ministepCount)).thenReturn(1d);
        when(logCalc.apply(diagonalLinksCount)).thenReturn(2d);
        when(logCalc.apply(1 - insertionProbability)).thenReturn(3d);
        when(logCalc.apply(insertionProbability)).thenReturn(4d);
        when(logCalc.apply(actorAspects)).thenReturn(5d);

        expectedRatio = 4 + 2 - 3 - 1 - 5;
    }

    public void testCalculateLogProposalRatio() throws Exception {
        DiagonalNetworkMinistepDeletionProposalRatioCalculator calculator = new DiagonalNetworkMinistepDeletionProposalRatioCalculator(logCalc, ministepChainCalculator);
        double result = calculator.calculateLogProposalRatio(insertionProbability, modification, state);
        assertThat(result).isEqualTo(expectedRatio);
    }
}
