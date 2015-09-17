/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.permute;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.Modification;
import net.lospi.juno.model.Model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class PermutationEgoLikelihoodRatioCalculatorTest {
    private Modification modification;
    private Chain state;
    private Model model;
    private double expected;

    @BeforeMethod
    public void setUp(){
        modification = mock(Modification.class);
        state = mock(Chain.class);
        model = mock(Model.class);
        expected = 0;
    }

    public void testCalculate() throws Exception {
        PermutationEgoLikelihoodRatioCalculator calculator = new PermutationEgoLikelihoodRatioCalculator();
        double result = calculator.calculate(modification, state, model);
        assertThat(result).isEqualTo(expected);
    }
}