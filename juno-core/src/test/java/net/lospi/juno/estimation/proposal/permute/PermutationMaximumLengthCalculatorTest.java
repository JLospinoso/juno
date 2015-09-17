/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.permute;

import net.lospi.juno.elements.Chain;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class PermutationMaximumLengthCalculatorTest {
    private int maximum, start;
    private Chain state;

    @BeforeMethod
    public void setUp() throws Exception {
        state = mock(Chain.class);
        start = 20;
        maximum = 10;
    }

    public void returnsMaxWhenEqualToChainLength() throws Exception {
        PermutationMaximumLengthCalculator calculator = new PermutationMaximumLengthCalculator();
        when(state.getSize()).thenReturn(start+maximum);
        int result = calculator.calculate(maximum, start, state);
        assertThat(result).isEqualTo(maximum);
    }

    public void returnsMaxWhenGreaterThanChainLength() throws Exception {
        PermutationMaximumLengthCalculator calculator = new PermutationMaximumLengthCalculator();
        when(state.getSize()).thenReturn(start+maximum+5);
        int result = calculator.calculate(maximum, start, state);
        assertThat(result).isEqualTo(maximum);
    }

    public void returnsAdjustedMaxWhenLessThanChainLength() throws Exception {
        PermutationMaximumLengthCalculator calculator = new PermutationMaximumLengthCalculator();
        when(state.getSize()).thenReturn(start+maximum-5);
        int result = calculator.calculate(maximum, start, state);
        assertThat(result).isEqualTo(maximum-5);
    }
}