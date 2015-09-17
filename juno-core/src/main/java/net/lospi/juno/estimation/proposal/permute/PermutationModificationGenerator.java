/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.permute;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.*;
import net.lospi.juno.model.Model;
import net.lospi.juno.stat.UniformDistribution;

public class PermutationModificationGenerator implements ModificationGenerator {
    private final UniformDistribution uniform;
    private final PermutationModificationBuilder builder;
    private final ChainSegmentReplacementAlterSelectionLogRatioCalculator alterSelectionLogRatioCalculator;
    private final PermutationChainSegmentReplacementGenerator permutationChainSegmentReplacementGenerator;
    private final PermutationMaximumLengthCalculator permutationMaximumLengthCalculator;
    private final int minSegmentLength;
    private final int maxSegmentLength;
    private final ChainSegmentAlterSelectionProbabilitiesCalculator chainSegmentAlterSelectionProbabilitiesCalculator;

    public PermutationModificationGenerator(UniformDistribution uniform, PermutationModificationBuilder builder,
                                            ChainSegmentReplacementAlterSelectionLogRatioCalculator alterSelectionLogRatioCalculator,
                                            PermutationChainSegmentReplacementGenerator permutationChainSegmentReplacementGenerator,
                                            PermutationMaximumLengthCalculator permutationMaximumLengthCalculator,
                                            int minSegmentLength, int maxSegmentLength,
                                            ChainSegmentAlterSelectionProbabilitiesCalculator chainSegmentAlterSelectionProbabilitiesCalculator) {
        this.uniform = uniform;
        this.builder = builder;
        this.alterSelectionLogRatioCalculator = alterSelectionLogRatioCalculator;
        this.permutationChainSegmentReplacementGenerator = permutationChainSegmentReplacementGenerator;
        this.permutationMaximumLengthCalculator = permutationMaximumLengthCalculator;
        this.minSegmentLength = minSegmentLength;
        this.maxSegmentLength = maxSegmentLength;
        this.chainSegmentAlterSelectionProbabilitiesCalculator = chainSegmentAlterSelectionProbabilitiesCalculator;
    }

    @Override
    public Modification generate(Chain state, Model model) {
        int minimumStartingIndex;
        if(state.containsPinnedLinks()){
            minimumStartingIndex = 1;
        } else {
            minimumStartingIndex = 0;
        }
        int maximumStartingIndex = state.getSize() - minSegmentLength;
        if(maximumStartingIndex < minimumStartingIndex) {
            return builder.with().identity().build();
        }
        int startingIndex = uniform.next(minimumStartingIndex, maximumStartingIndex);
        int adjustedMaxSegmentLength = permutationMaximumLengthCalculator.calculate(maxSegmentLength, startingIndex, state);
        int segmentLength = uniform.next(minSegmentLength, adjustedMaxSegmentLength);
        int endingIndex = startingIndex + segmentLength - 1;
        ChainSegment replacement = permutationChainSegmentReplacementGenerator.generate(state, startingIndex, endingIndex);
        chainSegmentAlterSelectionProbabilitiesCalculator.calculate(replacement.links(), state, model, startingIndex);
        double logAlterSelectionProbability = alterSelectionLogRatioCalculator.calculate(startingIndex, endingIndex, replacement, state);
        return builder.with()
                .start(startingIndex)
                .end(endingIndex)
                .replacement(replacement)
                .logAlterSelectionProbability(logAlterSelectionProbability)
                .build();
    }
}
