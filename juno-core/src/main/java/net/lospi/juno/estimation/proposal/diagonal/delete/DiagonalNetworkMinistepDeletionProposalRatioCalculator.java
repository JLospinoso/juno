/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.MinistepChainCalculator;
import net.lospi.juno.estimation.proposal.ProposalRatioCalculator;
import net.lospi.juno.stat.CachedNaturalLogarithm;

public class DiagonalNetworkMinistepDeletionProposalRatioCalculator
        implements ProposalRatioCalculator<DiagonalNetworkMinistepDeletionModification> {
    private CachedNaturalLogarithm naturalLogarithm;
    private final MinistepChainCalculator ministepChainCalculator;

    public DiagonalNetworkMinistepDeletionProposalRatioCalculator(CachedNaturalLogarithm naturalLogarithm,
                                                                  MinistepChainCalculator ministepChainCalculator) {
        this.naturalLogarithm = naturalLogarithm;
        this.ministepChainCalculator = ministepChainCalculator;
    }

    @Override
    public double calculateLogProposalRatio(double insertionProbability,
                                            DiagonalNetworkMinistepDeletionModification modification, Chain chain) {
        int diagonalLinksCount = ministepChainCalculator.getDiagonalLinksCount(chain);
        return naturalLogarithm.apply(insertionProbability)
                + naturalLogarithm.apply(diagonalLinksCount)
                - naturalLogarithm.apply(1D - insertionProbability)
                - naturalLogarithm.apply(chain.getSize())
                - naturalLogarithm.apply(chain.getActorAspectCount());
    }
}
