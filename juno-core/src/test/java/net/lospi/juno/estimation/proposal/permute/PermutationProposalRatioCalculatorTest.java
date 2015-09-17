/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */
package net.lospi.juno.estimation.proposal.permute;

import net.lospi.juno.elements.Chain;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class PermutationProposalRatioCalculatorTest {
    private Chain state;
    private PermutationModification modification;
    private double permutationProbability;
    private double expected;

    @BeforeMethod
    public void setUp() {
        state = mock(Chain.class);
        modification = mock(PermutationModification.class);
        permutationProbability = 0.75;
        expected = 0;
    }

    public void calculateLogProposalRatio() {
        PermutationProposalRatioCalculator underStudy = new PermutationProposalRatioCalculator();
        double result = underStudy.calculateLogProposalRatio(permutationProbability, modification, state);
        assertThat(result).isEqualTo(expected);
    }
}
