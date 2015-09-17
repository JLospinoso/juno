/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.insert;

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
public class DiagonalNetworkMinistepInsertionKappaFactorCalculatorTest {
    private CachedNaturalLogarithm naturalLogarithm;
    private Model model;
    private Chain chain;
    private Modification modification;
    private double expectedResult;

    @BeforeMethod
    public void setUp(){
        model = mock(Model.class);
        chain = mock(Chain.class);
        modification = mock(Modification.class);
        naturalLogarithm = mock(CachedNaturalLogarithm.class);

        when(model.globalRate()).thenReturn(10D);
        when(chain.getSize()).thenReturn(20);
        when(naturalLogarithm.apply(10D)).thenReturn(-2D);
        when(naturalLogarithm.apply(20 + 1)).thenReturn(-3D);
        expectedResult = 1D;
    }

    public void testCalculate() throws Exception {
        DiagonalNetworkMinistepInsertionKappaFactorCalculator factor = new DiagonalNetworkMinistepInsertionKappaFactorCalculator(naturalLogarithm);
        double result = factor.calculate(model, chain, modification);
        assertThat(result).isEqualTo(expectedResult);
    }
}
