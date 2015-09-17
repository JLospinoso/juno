/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import net.lospi.juno.elements.Chain;

public class ChainSegmentReplacementAlterSelectionLogRatioCalculator {
    private final ChainSegmentAlterSelectionProbabilityCalculator chainSegmentAlterSelectionProbabilityCalculator;

    public ChainSegmentReplacementAlterSelectionLogRatioCalculator(
            ChainSegmentAlterSelectionProbabilityCalculator chainSegmentAlterSelectionProbabilityCalculator) {
        this.chainSegmentAlterSelectionProbabilityCalculator = chainSegmentAlterSelectionProbabilityCalculator;
    }

    public double calculate(int segmentStart, int segmentEnd, ChainSegment replacement, Chain state) {
        ChainSegment currentSegment = state.segmentWithLikelihoodDerivatives(segmentStart, segmentEnd);
        double currentAlterSelectionLogProbability = chainSegmentAlterSelectionProbabilityCalculator.calculate(currentSegment);
        double proposedAlterSelectionLogProbability = chainSegmentAlterSelectionProbabilityCalculator.calculate(replacement);
        return proposedAlterSelectionLogProbability - currentAlterSelectionLogProbability;
    }
}
