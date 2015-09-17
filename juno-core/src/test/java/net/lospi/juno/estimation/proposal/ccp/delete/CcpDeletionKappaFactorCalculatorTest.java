/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.delete;

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
public class CcpDeletionKappaFactorCalculatorTest {
    private CachedNaturalLogarithm naturalLogarithm;
    private Model model;
    private Chain state;
    private Modification modification;
    private double expected;

    @BeforeMethod
    public void setUp(){
        naturalLogarithm = mock(CachedNaturalLogarithm.class);
        model = mock(Model.class);
        state = mock(Chain.class);
        modification = mock(Modification.class);

        when(model.globalRate()).thenReturn(20D);
        when(state.getSize()).thenReturn(30);
        when(naturalLogarithm.apply(20D)).thenReturn(1D);
        when(naturalLogarithm.apply(30)).thenReturn(3D);
        when(naturalLogarithm.apply(29)).thenReturn(4D);
        expected = 3 + 4 - 2;
    }

    public void testCalculate() throws Exception {
        CcpDeletionKappaFactorCalculator calculator = new CcpDeletionKappaFactorCalculator(naturalLogarithm);
        double result = calculator.calculate(model, state, modification);
        assertThat(result).isEqualTo(expected);
    }
}
