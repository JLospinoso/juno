/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import net.lospi.juno.elements.Chain;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class ProposalTest {
    private Modification modification;
    private double logAcceptanceProbability;
    private Chain state;

    @BeforeMethod
    public void setUp(){
        modification = mock(Modification.class);
        logAcceptanceProbability = -1234d;
        state = mock(Chain.class);
    }

    public void testGetModification() throws Exception {
        Proposal proposal = new Proposal(modification, logAcceptanceProbability);
        assertThat(proposal.getModification()).isEqualTo(modification);
    }

         public void testGetLogAcceptanceProbability() throws Exception {
        Proposal proposal = new Proposal(modification, logAcceptanceProbability);
        assertThat(proposal.getLogAcceptanceProbability()).isEqualTo(logAcceptanceProbability);
    }

    public void update() throws Exception {
        Proposal proposal = new Proposal(modification, logAcceptanceProbability);
        Chain returned = proposal.update(state);
        assertThat(returned).isEqualTo(state);
        verify(modification).modify(state);
    }
}
