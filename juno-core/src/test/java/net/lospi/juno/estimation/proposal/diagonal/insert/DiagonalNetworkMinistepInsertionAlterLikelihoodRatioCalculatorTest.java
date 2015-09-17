/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.insert;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.model.Model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class DiagonalNetworkMinistepInsertionAlterLikelihoodRatioCalculatorTest {
    private DiagonalNetworkMinistepInsertionModification modification;
    private Chain state;
    private Model model;
    private double alterSelectionProb;

    @BeforeMethod
    public void setUp(){
        alterSelectionProb = -42d;
        modification = mock(DiagonalNetworkMinistepInsertionModification.class);
        state = mock(Chain.class);
        model = mock(Model.class);
        when(modification.getLogAlterSelectionProbability()).thenReturn(alterSelectionProb);
    }

    public void testCalculate() throws Exception {
        DiagonalNetworkMinistepInsertionAlterLikelihoodRatioCalculator calculator = new DiagonalNetworkMinistepInsertionAlterLikelihoodRatioCalculator();
        double result = calculator.calculate(modification, state, model);
        assertThat(result).isEqualTo(alterSelectionProb);
    }
}
