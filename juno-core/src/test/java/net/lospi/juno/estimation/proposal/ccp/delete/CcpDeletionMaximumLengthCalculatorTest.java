/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.Link;
import net.lospi.juno.elements.MinistepChainCalculator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class CcpDeletionMaximumLengthCalculatorTest {
    private int maximum, start, chainLength;
    private Chain state;
    private Link link;
    private MinistepChainCalculator ministepChainCalculator;

    @BeforeMethod
    public void setUp() throws Exception {
        ministepChainCalculator = mock(MinistepChainCalculator.class);
        state = mock(Chain.class);
        link = mock(Link.class);
        start = 20;
        maximum = 10;
        chainLength = 31;

        when(state.getAt(start)).thenReturn(link);
        when(state.getSize()).thenReturn(chainLength);
    }

    public void returnsMaxOnNoLink() throws Exception {
        CcpDeletionMaximumLengthCalculator calculator = new CcpDeletionMaximumLengthCalculator(ministepChainCalculator);
        when(ministepChainCalculator.indexOfSecondMatchingLinkAfter(state, start, link)).thenReturn(null);
        int result = calculator.calculate(maximum, start, state);
        assertThat(result).isEqualTo(maximum);
    }

    public void shortensWhenNextLinkLessThanMax() throws Exception {
        CcpDeletionMaximumLengthCalculator calculator = new CcpDeletionMaximumLengthCalculator(ministepChainCalculator);
        when(ministepChainCalculator.indexOfSecondMatchingLinkAfter(state, start, link)).thenReturn(start + 10);
        int result = calculator.calculate(maximum, start, state);
        assertThat(result).isEqualTo(10 - 1);
    }

    public void returnsMaxOnGreaterThanMax() throws Exception {
        CcpDeletionMaximumLengthCalculator calculator = new CcpDeletionMaximumLengthCalculator(ministepChainCalculator);
        when(ministepChainCalculator.indexOfSecondMatchingLinkAfter(state, start, link)).thenReturn(start + 12);
        int result = calculator.calculate(maximum, start, state);
        assertThat(result).isEqualTo(maximum);
    }

    public void returnsChainLengthOnLengthLessThanMax() throws Exception {
        CcpDeletionMaximumLengthCalculator calculator = new CcpDeletionMaximumLengthCalculator(ministepChainCalculator);
        when(ministepChainCalculator.indexOfSecondMatchingLinkAfter(state, start, link)).thenReturn(null);
        when(state.getSize()).thenReturn(25);
        int result = calculator.calculate(maximum, start, state);
        assertThat(result).isEqualTo(25-20+1);
    }
}