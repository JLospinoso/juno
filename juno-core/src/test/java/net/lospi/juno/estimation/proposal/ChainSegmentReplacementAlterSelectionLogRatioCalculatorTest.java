/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import net.lospi.juno.elements.Chain;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class ChainSegmentReplacementAlterSelectionLogRatioCalculatorTest {
    private ChainSegmentAlterSelectionProbabilityCalculator chainSegmentAlterSelectionProbabilityCalculator;
    private int start, end;
    private ChainSegment replacement, currentSegment;
    private Chain state;
    private double expected;

    @BeforeMethod
    public void setUp() throws Exception {
        chainSegmentAlterSelectionProbabilityCalculator = mock(ChainSegmentAlterSelectionProbabilityCalculator.class);
        replacement = mock(ChainSegment.class);
        currentSegment = mock(ChainSegment.class);
        state = mock(Chain.class);
        start = 10;
        end = 20;
        when(state.segmentWithLikelihoodDerivatives(start, end)).thenReturn(currentSegment);
        when(chainSegmentAlterSelectionProbabilityCalculator.calculate(replacement)).thenReturn(-2d);
        when(chainSegmentAlterSelectionProbabilityCalculator.calculate(currentSegment)).thenReturn(-3d);
        expected = (-2d) - (-3d);
    }

    public void testCalculate() throws Exception {
        ChainSegmentReplacementAlterSelectionLogRatioCalculator calculator =
                new ChainSegmentReplacementAlterSelectionLogRatioCalculator(chainSegmentAlterSelectionProbabilityCalculator);
        double result = calculator.calculate(start, end, replacement, state);
        assertThat(result).isEqualTo(expected);
    }
}
