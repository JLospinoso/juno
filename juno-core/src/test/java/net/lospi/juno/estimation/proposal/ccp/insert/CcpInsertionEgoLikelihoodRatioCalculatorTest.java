/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.insert;

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
public class CcpInsertionEgoLikelihoodRatioCalculatorTest {
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
        when(state.getActorAspectCount()).thenReturn(10);
        when(naturalLogarithm.apply(10)).thenReturn(-123d);
        expected = -123d * -2;
    }

    public void testCalculate() throws Exception {
        CcpInsertionEgoLikelihoodRatioCalculator calculator = new CcpInsertionEgoLikelihoodRatioCalculator(naturalLogarithm);
        double result = calculator.calculate(modification, state, model);
        assertThat(result).isEqualTo(expected);
    }
}