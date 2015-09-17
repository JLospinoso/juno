/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */
package net.lospi.juno.estimation.proposal.ccp.insert;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.MinistepChainCalculator;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import net.lospi.juno.stat.CachedNaturalLogarithm;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups="unit")
public class CcpInsertionProposalRatioCalculatorTest {
    private CcpInsertionMaximumLengthCalculator ccpInsertionMaximumLengthCalculator;
    private Chain state;
    private CcpInsertionModification modification;
    private int maximumCcpLength;
    private CachedNaturalLogarithm naturalLogarithm;
    private int minimumCcpLength;
    private double insertionProbability;
    private double expectedPinned, expectedNotPinned;
    private MinistepChainCalculator ministepChainCalculator;

    @BeforeMethod
    public void setUp() {
        ministepChainCalculator = mock(MinistepChainCalculator.class);
        naturalLogarithm = mock(CachedNaturalLogarithm.class);
        modification = mock(CcpInsertionModification.class);
        ccpInsertionMaximumLengthCalculator = mock(CcpInsertionMaximumLengthCalculator.class);
        state = mock(Chain.class);
        minimumCcpLength = 2;
        insertionProbability = 0.75;
        maximumCcpLength = 40;
        int deletionStart = 30;
        int adjustedMaximumCcpLength = 40;
        int chainLength = 50;
        int actorAspectCount = 60;
        int alterCount = 70;
        int ccpCount = 80;
        ActorAspect actorAspect = mock(ActorAspect.class);
        when(modification.getStart()).thenReturn(deletionStart);
        when(ccpInsertionMaximumLengthCalculator.calculate(maximumCcpLength, deletionStart, state)).thenReturn(adjustedMaximumCcpLength);
        when(state.getSize()).thenReturn(chainLength);
        when(state.getActorAspectCount()).thenReturn(actorAspectCount);
        when(modification.getActorAspect()).thenReturn(actorAspect);
        when(ministepChainCalculator.getAlterCountFor(state, actorAspect)).thenReturn(alterCount);
        when(ministepChainCalculator.getConsecutivelyCancellingPairsCount(state, minimumCcpLength, maximumCcpLength)).thenReturn(ccpCount);

        when(naturalLogarithm.apply(insertionProbability)).thenReturn(1d);
        when(naturalLogarithm.apply(chainLength - minimumCcpLength + 1 - 1)).thenReturn(2d); //when pinned
        when(naturalLogarithm.apply(chainLength - minimumCcpLength + 1)).thenReturn(3d); //when not pinned
        when(naturalLogarithm.apply(actorAspectCount - 1)).thenReturn(4d);
        when(naturalLogarithm.apply(alterCount - 1)).thenReturn(5d);
        when(naturalLogarithm.apply(adjustedMaximumCcpLength - minimumCcpLength + 1)).thenReturn(6d);
        when(naturalLogarithm.apply(1 - insertionProbability)).thenReturn(7d);
        when(naturalLogarithm.apply(ccpCount + 1)).thenReturn(8d);

        expectedPinned = -1 + 2 + 4 + 5 + 6 + 7 - 8;
        expectedNotPinned = -1 + 3 + 4 + 5 + 6 + 7 - 8;
    }

    public void calculateLogProposalRatioPinned() {
        CcpInsertionProposalRatioCalculator underStudy = new CcpInsertionProposalRatioCalculator(naturalLogarithm, ccpInsertionMaximumLengthCalculator, minimumCcpLength, maximumCcpLength, ministepChainCalculator);
        when(state.containsPinnedLinks()).thenReturn(true);
        double result = underStudy.calculateLogProposalRatio(insertionProbability, modification, state);
        assertThat(result).isEqualTo(expectedPinned);
    }

    public void calculateLogProposalRatioNotPinned() {
        CcpInsertionProposalRatioCalculator underStudy = new CcpInsertionProposalRatioCalculator(naturalLogarithm, ccpInsertionMaximumLengthCalculator, minimumCcpLength, maximumCcpLength, ministepChainCalculator);
        when(state.containsPinnedLinks()).thenReturn(false);
        double result = underStudy.calculateLogProposalRatio(insertionProbability, modification, state);
        assertThat(result).isEqualTo(expectedNotPinned);
    }

}
