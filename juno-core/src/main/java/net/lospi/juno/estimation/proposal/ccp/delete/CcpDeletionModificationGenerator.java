/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.MinistepChainCalculator;
import net.lospi.juno.estimation.proposal.ChainSegment;
import net.lospi.juno.estimation.proposal.ChainSegmentReplacementAlterSelectionLogRatioCalculator;
import net.lospi.juno.estimation.proposal.Modification;
import net.lospi.juno.estimation.proposal.ModificationGenerator;
import net.lospi.juno.estimation.proposal.ChainSegmentAlterSelectionProbabilitiesCalculator;
import net.lospi.juno.model.Model;
import net.lospi.juno.stat.UniformDistribution;
import org.javatuples.Pair;

import java.util.List;

public class CcpDeletionModificationGenerator implements ModificationGenerator {
    private final UniformDistribution uniform;
    private final CcpDeletionModificationBuilder builder;
    private final ChainSegmentReplacementAlterSelectionLogRatioCalculator chainSegmentReplacementAlterSelectionLogRatioCalculator;
    private final int minSegmentLength;
    private final int maxSegmentLength;
    private final CcpDeletionChainSegmentReplacementGenerator ccpDeletionChainSegmentReplacementGenerator;
    private final ChainSegmentAlterSelectionProbabilitiesCalculator chainSegmentAlterSelectionProbabilitiesCalculator;
    private final MinistepChainCalculator ministepChainCalculator;

    public CcpDeletionModificationGenerator(UniformDistribution uniform, CcpDeletionModificationBuilder builder,
                                            ChainSegmentReplacementAlterSelectionLogRatioCalculator chainSegmentReplacementAlterSelectionLogRatioCalculator,
                                            int minSegmentLength, int maxSegmentLength, CcpDeletionChainSegmentReplacementGenerator ccpDeletionChainSegmentReplacementGenerator, ChainSegmentAlterSelectionProbabilitiesCalculator chainSegmentAlterSelectionProbabilitiesCalculator, MinistepChainCalculator ministepChainCalculator) {
        this.uniform = uniform;
        this.builder = builder;
        this.chainSegmentReplacementAlterSelectionLogRatioCalculator = chainSegmentReplacementAlterSelectionLogRatioCalculator;
        this.minSegmentLength = minSegmentLength;
        this.maxSegmentLength = maxSegmentLength;
        this.ccpDeletionChainSegmentReplacementGenerator = ccpDeletionChainSegmentReplacementGenerator;
        this.chainSegmentAlterSelectionProbabilitiesCalculator = chainSegmentAlterSelectionProbabilitiesCalculator;
        this.ministepChainCalculator = ministepChainCalculator;
    }

    @Override
    public Modification generate(Chain state, Model model) {
        List<Pair<Integer, Integer>> ccpIndices = ministepChainCalculator.getConsecutivelyCancelingPairs(state, minSegmentLength, maxSegmentLength);
        if(ccpIndices.isEmpty()){
            return builder.with().identity().build();
        }
        Pair<Integer, Integer> deletionIndices = uniform.next(ccpIndices);
        int segmentStart = deletionIndices.getValue0();
        int segmentEnd = deletionIndices.getValue1();
        ChainSegment replacement = ccpDeletionChainSegmentReplacementGenerator.generate(state, segmentStart, segmentEnd);
        chainSegmentAlterSelectionProbabilitiesCalculator.calculate(replacement.links(), state, model, segmentStart);
        double logAlterSelectionProbability = chainSegmentReplacementAlterSelectionLogRatioCalculator.calculate(segmentStart, segmentEnd, replacement, state);
        return builder.with()
                .start(segmentStart)
                .end(segmentEnd)
                .replacement(replacement)
                .logAlterSelectionProbability(logAlterSelectionProbability)
                .build();
    }
}
