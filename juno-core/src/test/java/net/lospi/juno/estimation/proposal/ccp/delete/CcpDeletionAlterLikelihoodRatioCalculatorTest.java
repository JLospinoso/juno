/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.model.Model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

@Test(groups="unit")
public class CcpDeletionAlterLikelihoodRatioCalculatorTest {
    private CcpDeletionModification modification;
    private Chain state;
    private Model model;
    double expected;

    @BeforeMethod
    public void setUp(){
        modification = mock(CcpDeletionModification.class);
        state = mock(Chain.class);
        model = mock(Model.class);
        expected = -123d;
    }

    public void testCalculate() throws Exception {
        CcpDeletionAlterLikelihoodRatioCalculator calculator = new CcpDeletionAlterLikelihoodRatioCalculator();
        when(modification.getLogAlterSelectionProbability()).thenReturn(expected);
        double result = calculator.calculate(modification, state, model);
        assertThat(result).isEqualTo(expected);
    }
}