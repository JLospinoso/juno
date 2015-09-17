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
public class CcpInsertionKappaFactorCalculatorTest {
    private Modification modification;
    private Chain state;
    private Model model;
    private CachedNaturalLogarithm naturalLogarithm;
    private double expected;

    @BeforeMethod
    public void setUp() {
        model = mock(Model.class);
        naturalLogarithm = mock(CachedNaturalLogarithm.class);
        state = mock(Chain.class);
        modification = mock(Modification.class);
        when(model.globalRate()).thenReturn(5d);
        when(state.getSize()).thenReturn(20);
        when(naturalLogarithm.apply(5d)).thenReturn(1d);
        when(naturalLogarithm.apply(21)).thenReturn(-2d);
        when(naturalLogarithm.apply(22)).thenReturn(-3d);
        expected = 2+2+3;
    }

    public void calculate() {
        CcpInsertionKappaFactorCalculator underStudy = new CcpInsertionKappaFactorCalculator(naturalLogarithm);
        double result = underStudy.calculate(model, state, modification);
        assertThat(result).isEqualTo(expected);
    }
}
