/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.insert;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.MinistepChainCalculator;
import net.lospi.juno.estimation.proposal.ProposalRatioCalculator;
import net.lospi.juno.stat.CachedNaturalLogarithm;

public class CcpInsertionProposalRatioCalculator implements ProposalRatioCalculator<CcpInsertionModification> {
    private final CachedNaturalLogarithm naturalLogarithm;
    private final CcpInsertionMaximumLengthCalculator ccpInsertionMaximumLengthCalculator;
    private final int minimumCcpLength;
    private final int maximumCcpLength;
    private final MinistepChainCalculator ministepChainCalculator;

    public CcpInsertionProposalRatioCalculator(CachedNaturalLogarithm naturalLogarithm,
                                               CcpInsertionMaximumLengthCalculator ccpInsertionMaximumLengthCalculator,
                                               int minimumCcpLength, int maximumCcpLength, MinistepChainCalculator ministepChainCalculator) {
        this.naturalLogarithm = naturalLogarithm;
        this.ccpInsertionMaximumLengthCalculator = ccpInsertionMaximumLengthCalculator;
        this.minimumCcpLength = minimumCcpLength;
        this.maximumCcpLength = maximumCcpLength;
        this.ministepChainCalculator = ministepChainCalculator;
    }

    @Override
    public double calculateLogProposalRatio(double insertionProbability, CcpInsertionModification modification, Chain state) {
        int pinAdjustment;
        if(state.containsPinnedLinks()){
            pinAdjustment = 1;
        } else {
            pinAdjustment = 0;
        }
        int insertionStart = modification.getStart();
        int adjustedMaximumCcpLength = ccpInsertionMaximumLengthCalculator.calculate(maximumCcpLength, insertionStart, state);
        int chainLength = state.getSize();
        int waysToSelectStartingIndex = chainLength - minimumCcpLength + 1 - pinAdjustment;
        int actorAspects = state.getActorAspectCount();
        int alters = ministepChainCalculator.getAlterCountFor(state, modification.getActorAspect());
        int intervalSizePossibilities = adjustedMaximumCcpLength - minimumCcpLength + 1;

        double insertionProposalProbability =
                      naturalLogarithm.apply(insertionProbability)
                -1D * naturalLogarithm.apply(waysToSelectStartingIndex)
                -1D * naturalLogarithm.apply(actorAspects - 1)
                -1D * naturalLogarithm.apply(alters - 1)
                -1D * naturalLogarithm.apply(intervalSizePossibilities);

        int waysToDeleteCcp = ministepChainCalculator.getConsecutivelyCancellingPairsCount(state, minimumCcpLength, maximumCcpLength) + 1;
        double deletionProposalProbability =
                      naturalLogarithm.apply(1 - insertionProbability)
                 -1D * naturalLogarithm.apply(waysToDeleteCcp);

        return deletionProposalProbability - insertionProposalProbability;
    }
}

