/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.permute;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.model.Model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class PermutationAlterLikelihoodRatioCalculatorTest {
    private PermutationModification modification;
    private Chain state;
    private Model model;
    double expected;

    @BeforeMethod
    public void setUp(){
        modification = mock(PermutationModification.class);
        state = mock(Chain.class);
        model = mock(Model.class);
        expected = -123d;
    }

    public void testCalculate() throws Exception {
        PermutationAlterLikelihoodRatioCalculator calculator = new PermutationAlterLikelihoodRatioCalculator();
        when(modification.getLogAlterSelectionProbability()).thenReturn(expected);
        double result = calculator.calculate(modification, state, model);
        assertThat(result).isEqualTo(expected);
    }
}