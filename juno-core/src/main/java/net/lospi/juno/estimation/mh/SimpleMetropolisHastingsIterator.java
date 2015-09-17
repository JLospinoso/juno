/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.mh;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.Proposal;
import net.lospi.juno.estimation.proposal.ProposalGenerator;
import net.lospi.juno.model.Model;
import net.lospi.juno.stat.BooleanDistribution;

public class SimpleMetropolisHastingsIterator implements MetropolisHastingsIterator {
    private final ProposalGenerator proposalGenerator;
    private final BooleanDistribution booleanDistribution;
    private final SimpleMetropolisHastingsStepBuilder simpleMetropolisHastingsStepBuilder;

    public SimpleMetropolisHastingsIterator(ProposalGenerator proposalGenerator, BooleanDistribution booleanDistribution,
                                            SimpleMetropolisHastingsStepBuilder simpleMetropolisHastingsStepBuilder) {
        this.proposalGenerator = proposalGenerator;
        this.booleanDistribution = booleanDistribution;
        this.simpleMetropolisHastingsStepBuilder = simpleMetropolisHastingsStepBuilder;
    }

    @Override
    public MetropolisHastingsStep next(Chain state, Model model){
        Proposal proposal = proposalGenerator.generate(state, model);
        boolean accept = booleanDistribution.nextWithLogProbability(proposal.getLogAcceptanceProbability());
        if(accept){
            proposal.update(state);
            return simpleMetropolisHastingsStepBuilder
                    .with()
                    .accepted(true)
                    .proposal(proposal)
                    .state(state)
                    .model(model)
                    .build();
        } else {
            return simpleMetropolisHastingsStepBuilder
                    .with()
                    .accepted(false)
                    .proposal(proposal)
                    .state(state)
                    .model(model)
                    .build();
        }
    }
}
