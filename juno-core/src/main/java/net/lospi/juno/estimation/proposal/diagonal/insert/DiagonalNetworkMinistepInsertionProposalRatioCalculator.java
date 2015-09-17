/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.insert;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.MinistepChainCalculator;
import net.lospi.juno.estimation.proposal.ProposalRatioCalculator;
import net.lospi.juno.stat.CachedNaturalLogarithm;

public class DiagonalNetworkMinistepInsertionProposalRatioCalculator
        implements ProposalRatioCalculator<DiagonalNetworkMinistepInsertionModification> {
    private CachedNaturalLogarithm naturalLogarithm;
    private final MinistepChainCalculator ministepChainCalculator;

    public DiagonalNetworkMinistepInsertionProposalRatioCalculator(CachedNaturalLogarithm naturalLogarithm,
                                                                   MinistepChainCalculator ministepChainCalculator) {
        this.naturalLogarithm = naturalLogarithm;
        this.ministepChainCalculator = ministepChainCalculator;
    }

    @Override
    public double calculateLogProposalRatio(double insertionProbability,
                                            DiagonalNetworkMinistepInsertionModification modification, Chain state) {
        int diagonalLinksCount = ministepChainCalculator.getDiagonalLinksCount(state);
        return naturalLogarithm.apply(1 - insertionProbability)
             - naturalLogarithm.apply(insertionProbability)
             + naturalLogarithm.apply(state.getSize() + 1)
             + naturalLogarithm.apply(state.getActorAspectCount())
             - naturalLogarithm.apply(diagonalLinksCount + 1);
    }
}
