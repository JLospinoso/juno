/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.permute;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.ProposalRatioCalculator;

public class PermutationProposalRatioCalculator implements ProposalRatioCalculator<PermutationModification> {

    @Override
    public double calculateLogProposalRatio(double proposalProbability, PermutationModification modification, Chain state) {
        return 0;
    }
}

