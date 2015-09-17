/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.insert;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.Link;
import net.lospi.juno.elements.Ministep;
import net.lospi.juno.elements.MinistepChainCalculator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class CcpInsertionMaximumLengthCalculatorTest {
    private int maximum, start;
    private Chain state;
    private Link link;
    private MinistepChainCalculator ministepChainCalculator;

    @BeforeMethod
    public void setUp() throws Exception {
        state = mock(Chain.class);
        link = mock(Link.class);
        start = 20;
        maximum = 10;
        ministepChainCalculator = mock(MinistepChainCalculator.class);
        when(state.getAt(start)).thenReturn(link);
    }

    public void returnsMaxOnNoLink() throws Exception {
        ministepChainCalculator = mock(MinistepChainCalculator.class);
        CcpInsertionMaximumLengthCalculator calculator = new CcpInsertionMaximumLengthCalculator(ministepChainCalculator);
        when(ministepChainCalculator.indexOfNextMatchingLinkAfter(state, start, link)).thenReturn(null);
        when(state.getSize()).thenReturn(start+maximum);
        int result = calculator.calculate(maximum, start, state);
        assertThat(result).isEqualTo(maximum);
    }

    public void returnsMaxMinusDifferenceToEndOnNoLink() throws Exception {
        int maximumIsTooBigBy = 3;
        CcpInsertionMaximumLengthCalculator calculator = new CcpInsertionMaximumLengthCalculator(ministepChainCalculator);
        when(ministepChainCalculator.indexOfNextMatchingLinkAfter(state, start, link)).thenReturn(null);
        when(state.getSize()).thenReturn(start+maximum-maximumIsTooBigBy);
        int result = calculator.calculate(maximum, start, state);
        assertThat(result).isEqualTo(maximum-maximumIsTooBigBy);
    }

    public void shortensWhenNextLinkLessThanMax() throws Exception {
        CcpInsertionMaximumLengthCalculator calculator = new CcpInsertionMaximumLengthCalculator(ministepChainCalculator);
        when(ministepChainCalculator.indexOfNextMatchingLinkAfter(state, start, link)).thenReturn(start + 8);
        when(state.getSize()).thenReturn(start+maximum+100);
        int result = calculator.calculate(maximum, start, state);
        assertThat(result).isEqualTo(8);
    }

    public void returnsMaxOnGreaterThanMax() throws Exception {
        CcpInsertionMaximumLengthCalculator calculator = new CcpInsertionMaximumLengthCalculator(ministepChainCalculator);
        when(ministepChainCalculator.indexOfNextMatchingLinkAfter(state, start, link)).thenReturn(start + 12);
        int result = calculator.calculate(maximum, start, state);
        when(state.getSize()).thenReturn(start+maximum+100);
        assertThat(result).isEqualTo(maximum);
    }
}