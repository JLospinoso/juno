/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

public interface ProposalDistributionBuilder {
    ProposalDistributionBuilder with(ProposalGenerator proposalGenerator, double measure);

    ProposalDistribution build();
}
