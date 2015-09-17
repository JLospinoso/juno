/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.mh;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.Proposal;
import net.lospi.juno.estimation.proposal.ProposalGenerator;
import net.lospi.juno.model.Model;
import net.lospi.juno.stat.BooleanDistribution;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class SimpleMetropolisHastingsIteratorIteratorTest {
    private ProposalGenerator proposalGenerator;
    private BooleanDistribution booleanDistribution;
    private SimpleMetropolisHastingsStepBuilder simpleMetropolisHastingsStepBuilder;
    private Chain state;
    private Model model;
    private SimpleMetropolisHastingsStep expected;
    private Proposal proposal;
    private double logAcceptProbability;
    private SimpleMetropolisHastingsStepBuilder.Stub builderStub;

    @BeforeMethod
    public void setUp(){
        state = mock(Chain.class);
        model = mock(Model.class);
        proposalGenerator = mock(ProposalGenerator.class);
        booleanDistribution = mock(BooleanDistribution.class);
        simpleMetropolisHastingsStepBuilder = mock(SimpleMetropolisHastingsStepBuilder.class);
        expected = mock(SimpleMetropolisHastingsStep.class);
        proposal = mock(Proposal.class);
        builderStub = mock(SimpleMetropolisHastingsStepBuilder.Stub.class);
        logAcceptProbability = -1d;

        when(proposalGenerator.generate(state, model)).thenReturn(proposal);
        when(simpleMetropolisHastingsStepBuilder.with()).thenReturn(builderStub);
        when(builderStub.model(model)).thenReturn(builderStub);
        when(builderStub.proposal(proposal)).thenReturn(builderStub);
        when(builderStub.state(state)).thenReturn(builderStub);
        when(builderStub.build()).thenReturn(expected);

        when(proposal.getLogAcceptanceProbability()).thenReturn(logAcceptProbability);
    }

    public void nextAccept() throws Exception {
        when(booleanDistribution.nextWithLogProbability(logAcceptProbability)).thenReturn(true);
        when(builderStub.accepted(true)).thenReturn(builderStub);

        SimpleMetropolisHastingsIterator iterator = new SimpleMetropolisHastingsIterator(proposalGenerator, booleanDistribution, simpleMetropolisHastingsStepBuilder);
        MetropolisHastingsStep result = iterator.next(state, model);
        assertThat(result).isEqualTo(expected);

        verify(proposal).update(state);
        verify(builderStub).model(model);
        verify(builderStub).proposal(proposal);
        verify(builderStub).state(state);
    }

    public void nextReject() throws Exception {
        when(booleanDistribution.nextWithLogProbability(logAcceptProbability)).thenReturn(false);
        when(builderStub.accepted(false)).thenReturn(builderStub);

        SimpleMetropolisHastingsIterator iterator = new SimpleMetropolisHastingsIterator(proposalGenerator, booleanDistribution, simpleMetropolisHastingsStepBuilder);
        MetropolisHastingsStep result = iterator.next(state, model);
        assertThat(result).isEqualTo(expected);

        verify(proposal, never()).update(state);
        verify(builderStub).model(model);
        verify(builderStub).proposal(proposal);
        verify(builderStub).state(state);
    }
}
