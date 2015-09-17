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
public class PermutationKappaFactorCalculatorTest {
    private Modification modification;
    private Chain state;
    private Model model;
    private double expected;

    @BeforeMethod
    public void setUp() {
        model = mock(Model.class);
        state = mock(Chain.class);
        modification = mock(Modification.class);
        expected = 0;
    }

    public void calculate() {
        PermutationKappaFactorCalculator underStudy = new PermutationKappaFactorCalculator();
        double result = underStudy.calculate(model, state, modification);
        assertThat(result).isEqualTo(expected);
    }
}
