/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.mh;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.Proposal;
import net.lospi.juno.model.Model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class SimpleMetropolisHastingsIteratorStepTest {
    private boolean accepted;
    private Model model;
    private Chain state;
    private Proposal proposal;

    @BeforeMethod
    public void setUp() throws Exception {
        accepted = true;
        model = mock(Model.class);
        state = mock(Chain.class);
        proposal = mock(Proposal.class);
    }

    public void testGetModel() throws Exception {
        SimpleMetropolisHastingsStep step = new SimpleMetropolisHastingsStep(model, state, proposal, accepted);
        assertThat(step.getModel()).isEqualTo(model);
    }

    public void testGetState() throws Exception {
        SimpleMetropolisHastingsStep step = new SimpleMetropolisHastingsStep(model, state, proposal, accepted);
        assertThat(step.getState()).isEqualTo(state);
    }

    public void testGetProposal() throws Exception {
        SimpleMetropolisHastingsStep step = new SimpleMetropolisHastingsStep(model, state, proposal, accepted);
        assertThat(step.getProposal()).isEqualTo(proposal);
    }

    public void testIsAccepted() throws Exception {
        SimpleMetropolisHastingsStep step = new SimpleMetropolisHastingsStep(model, state, proposal, accepted);
        assertThat(step.isAccepted()).isEqualTo(accepted);
    }
}
