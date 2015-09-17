/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */
package net.lospi.juno.estimation.proposal;

import net.lospi.juno.elements.Chain;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import net.lospi.juno.stat.EmpiricalDistribution;
import net.lospi.juno.model.Model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups="unit")
public class ConglomerateProposalGeneratorTest {
    private Chain state;
    private Model model;
    private EmpiricalDistribution proposals;
    private ProposalGenerator proposalGenerator;
    private Proposal expected;

    @BeforeMethod
    public void setUp() {
        proposals = mock(EmpiricalDistribution.class);
        model = mock(Model.class);
        state = mock(Chain.class);
        proposalGenerator = mock(ProposalGenerator.class);
        expected = mock(Proposal.class);
        when(proposals.next()).thenReturn(proposalGenerator);
        when(proposalGenerator.generate(state, model)).thenReturn(expected);
    }

    public void generate() {
        ConglomerateProposalGenerator underStudy = new ConglomerateProposalGenerator(proposals);
        Proposal result = underStudy.generate(state, model);
        assertThat(result).isEqualTo(expected);
    }
}
