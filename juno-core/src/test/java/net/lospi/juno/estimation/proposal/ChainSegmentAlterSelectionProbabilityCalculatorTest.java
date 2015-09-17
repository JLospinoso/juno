/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.elements.Link;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class ChainSegmentAlterSelectionProbabilityCalculatorTest {
    private ChainSegment replacement;
    private double expected;
    private List<Link> links;
    private Link link1, link2;
    private LikelihoodDerivatives derivatives1, derivatives2;

    @BeforeMethod
    public void setUp() throws Exception {
        derivatives1 = mock(LikelihoodDerivatives.class);
        derivatives2 = mock(LikelihoodDerivatives.class);
        replacement = mock(ChainSegment.class);
        link1 = mock(Link.class);
        link2 = mock(Link.class);
        when(link1.getLikelihoodDerivatives()).thenReturn(derivatives1);
        when(link2.getLikelihoodDerivatives()).thenReturn(derivatives2);
        when(derivatives1.getLogLikelihood()).thenReturn(-4d);
        when(derivatives2.getLogLikelihood()).thenReturn(-8d);
        expected = -12d;


        links = ImmutableList.of(link1, link2);
        when(replacement.links()).thenReturn(links);
    }

    public void testCalculate() throws Exception {
        ChainSegmentAlterSelectionProbabilityCalculator calculator = new ChainSegmentAlterSelectionProbabilityCalculator();
        double result = calculator.calculate(replacement);
        assertThat(result).isEqualTo(expected);
    }
}