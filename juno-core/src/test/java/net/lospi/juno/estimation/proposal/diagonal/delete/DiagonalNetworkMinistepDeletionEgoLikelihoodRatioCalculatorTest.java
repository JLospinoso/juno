/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.Modification;
import net.lospi.juno.model.Model;
import net.lospi.juno.stat.CachedNaturalLogarithm;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class DiagonalNetworkMinistepDeletionEgoLikelihoodRatioCalculatorTest {
    private CachedNaturalLogarithm naturalLogarithm;
    private Modification modification;
    private Chain state;
    private Model model;
    private double expected;

    @BeforeMethod
    public void setUp(){
        naturalLogarithm = mock(CachedNaturalLogarithm.class);
        modification = mock(Modification.class);
        state = mock(Chain.class);
        model = mock(Model.class);
        when(state.getActorAspectCount()).thenReturn(20);
        when(naturalLogarithm.apply(20)).thenReturn(5d);
        expected = 5D;
    }

    public void testCalculate() throws Exception {
        DiagonalNetworkMinistepDeletionEgoLikelihoodRatioCalculator calculator = new DiagonalNetworkMinistepDeletionEgoLikelihoodRatioCalculator(naturalLogarithm);
        double result = calculator.calculate(modification, state, model);
        assertThat(result).isEqualTo(expected);
    }
}
