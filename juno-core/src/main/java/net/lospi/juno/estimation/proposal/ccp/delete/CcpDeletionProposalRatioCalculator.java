/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.MinistepChainCalculator;
import net.lospi.juno.estimation.proposal.ProposalRatioCalculator;
import net.lospi.juno.stat.CachedNaturalLogarithm;

public class CcpDeletionProposalRatioCalculator implements ProposalRatioCalculator<CcpDeletionModification> {
    private final CachedNaturalLogarithm naturalLogarithm;
    private final CcpDeletionMaximumLengthCalculator ccpDeletionMaximumLengthCalculator;
    private final MinistepChainCalculator ministepChainCalculator;
    private final int minimumCcpLength;
    private final int maximumCcpLength;

    public CcpDeletionProposalRatioCalculator(CachedNaturalLogarithm naturalLogarithm,
                                              CcpDeletionMaximumLengthCalculator ccpDeletionMaximumLengthCalculator,
                                              MinistepChainCalculator ministepChainCalculator,
                                              int minimumCcpLength, int maximumCcpLength) {
        this.naturalLogarithm = naturalLogarithm;
        this.ccpDeletionMaximumLengthCalculator = ccpDeletionMaximumLengthCalculator;
        this.ministepChainCalculator = ministepChainCalculator;
        this.minimumCcpLength = minimumCcpLength;
        this.maximumCcpLength = maximumCcpLength;
    }

    @Override
    public double calculateLogProposalRatio(double insertionProbability, CcpDeletionModification modification, Chain state) {
        int deletionStart = modification.getStart();
        int adjustedMaximumCcpLength = ccpDeletionMaximumLengthCalculator.calculate(maximumCcpLength, deletionStart, state);
        int ccpCount = ministepChainCalculator.getConsecutivelyCancellingPairsCount(state, minimumCcpLength, maximumCcpLength);
        return naturalLogarithm.apply(insertionProbability)
                - 2D * naturalLogarithm.apply(state.getActorAspectCount() - 1)
                - naturalLogarithm.apply(state.getSize() - minimumCcpLength)
                - naturalLogarithm.apply(adjustedMaximumCcpLength - minimumCcpLength + 1)
                - 2D * naturalLogarithm.apply(1 - insertionProbability)
                + naturalLogarithm.apply(ccpCount);
    }
}
