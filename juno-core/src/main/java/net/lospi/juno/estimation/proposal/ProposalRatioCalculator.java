/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import net.lospi.juno.elements.Chain;

public interface ProposalRatioCalculator<T extends Modification> {
    double calculateLogProposalRatio(double insertionProbability, T modification, Chain state);
}
