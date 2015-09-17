/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.diagonal.insert.DiagonalNetworkMinistepInsertionModification;
import net.lospi.juno.model.Model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class DiagonalNetworkMinistepDeletionAlterLikelihoodRatioCalculatorTest {
    private DiagonalNetworkMinistepDeletionModification modification;
    private Chain state;
    private Model model;
    private double alterSelectionProb;

    @BeforeMethod
    public void setUp(){
        alterSelectionProb = -42d;
        modification = mock(DiagonalNetworkMinistepDeletionModification.class);
        state = mock(Chain.class);
        model = mock(Model.class);
        when(modification.getLogAlterSelectionProbability()).thenReturn(alterSelectionProb);
    }

    public void testCalculate() throws Exception {
        DiagonalNetworkMinistepDeletionAlterLikelihoodRatioCalculator calculator = new DiagonalNetworkMinistepDeletionAlterLikelihoodRatioCalculator();
        double result = calculator.calculate(modification, state, model);
        assertThat(result).isEqualTo(-1D * alterSelectionProb);
    }
}
