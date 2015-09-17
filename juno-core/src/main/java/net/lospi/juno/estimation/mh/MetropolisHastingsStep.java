/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.mh;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.Proposal;
import net.lospi.juno.model.Model;

public interface MetropolisHastingsStep {
    Model getModel();

    Chain getState();

    Proposal getProposal();

    boolean isAccepted();
}
