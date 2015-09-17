/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.model.Model;
import net.lospi.juno.stat.EmpiricalDistribution;

public class ConglomerateProposalGenerator implements ProposalGenerator {
    private final EmpiricalDistribution<ProposalGenerator> proposals;

    public ConglomerateProposalGenerator(EmpiricalDistribution<ProposalGenerator> proposals) {
        this.proposals = proposals;
    }

    @Override
    public Proposal generate(Chain state, Model model) {
        ProposalGenerator nextProposal = proposals.next();
        return nextProposal.generate(state, model);
    }
}
