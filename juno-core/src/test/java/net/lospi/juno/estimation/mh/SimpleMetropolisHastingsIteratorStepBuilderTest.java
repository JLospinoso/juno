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
public class SimpleMetropolisHastingsIteratorStepBuilderTest {
    private boolean accepted;
    private Model model;
    private Proposal proposal;
    private Chain state;

    @BeforeMethod
    public void setUp(){
        accepted = true;
        model = mock(Model.class);
        proposal = mock(Proposal.class);
        state = mock(Chain.class);
    }

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "You must set accepted or not.")
    public void testAccepted() throws Exception {
        SimpleMetropolisHastingsStepBuilder simpleMetropolisHastingsStepBuilder = new SimpleMetropolisHastingsStepBuilder();
        simpleMetropolisHastingsStepBuilder.with()
                .model(model)
                .proposal(proposal)
                .state(state)
                .build();
    }

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "You must set the proposal.")
    public void testProposal() throws Exception {
        SimpleMetropolisHastingsStepBuilder simpleMetropolisHastingsStepBuilder = new SimpleMetropolisHastingsStepBuilder();
        simpleMetropolisHastingsStepBuilder.with()
                .accepted(accepted)
                .model(model)
                .state(state)
                .build();
    }

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "You must set the state.")
    public void testState() throws Exception {
        SimpleMetropolisHastingsStepBuilder simpleMetropolisHastingsStepBuilder = new SimpleMetropolisHastingsStepBuilder();
        simpleMetropolisHastingsStepBuilder.with()
                .accepted(accepted)
                .model(model)
                .proposal(proposal)
                .build();
    }

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "You must set the model.")
    public void testModel() throws Exception {
        SimpleMetropolisHastingsStepBuilder simpleMetropolisHastingsStepBuilder = new SimpleMetropolisHastingsStepBuilder();
        simpleMetropolisHastingsStepBuilder.with()
                .accepted(accepted)
                .proposal(proposal)
                .state(state)
                .build();
    }

    public void testBuild() throws Exception {
        SimpleMetropolisHastingsStepBuilder simpleMetropolisHastingsStepBuilder = new SimpleMetropolisHastingsStepBuilder();
        SimpleMetropolisHastingsStep result = simpleMetropolisHastingsStepBuilder.with()
                .accepted(accepted)
                .model(model)
                .proposal(proposal)
                .state(state)
                .build();
        assertThat(result.isAccepted()).isEqualTo(accepted);
        assertThat(result.getModel()).isEqualTo(model);
        assertThat(result.getProposal()).isEqualTo(proposal);
        assertThat(result.getState()).isEqualTo(state);
    }
}
