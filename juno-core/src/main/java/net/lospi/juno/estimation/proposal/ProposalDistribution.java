/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.model.Model;

public interface ProposalDistribution {
    Proposal propose(Chain state, Model model);
}
