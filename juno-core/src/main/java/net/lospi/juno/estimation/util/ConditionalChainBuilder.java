/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.util;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.ProposalDistribution;
import net.lospi.juno.estimation.sim.ChainSimulator;

public interface ConditionalChainBuilder {
    ConditionalChainBuilder with();

    ConditionalChainBuilder initialChain(Chain chain);

    ConditionalChainBuilder proposalDistribution(ProposalDistribution proposalDistribution);

    ChainSimulator build();
}
