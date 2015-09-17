/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.mh;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.Proposal;
import net.lospi.juno.model.Model;

public class SimpleMetropolisHastingsStep implements MetropolisHastingsStep {
    private final Model model;
    private final Chain state;
    private final Proposal proposal;
    private final boolean accepted;

    public SimpleMetropolisHastingsStep(Model model, Chain state, Proposal proposal, boolean accepted) {

        this.model = model;
        this.state = state;
        this.proposal = proposal;
        this.accepted = accepted;
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public Chain getState() {
        return state;
    }

    @Override
    public Proposal getProposal() {
        return proposal;
    }

    @Override
    public boolean isAccepted() {
        return accepted;
    }
}
