/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.insert;

import net.lospi.juno.elements.*;
import net.lospi.juno.estimation.proposal.*;
import net.lospi.juno.model.Model;
import net.lospi.juno.stat.UniformDistribution;

import java.util.List;

public class CcpInsertionModificationGenerator implements ModificationGenerator {
    private final UniformDistribution uniform;
    private final CcpInsertionModificationBuilder builder;
    private final ChainSegmentReplacementAlterSelectionLogRatioCalculator alterSelectionLogRatioCalculator;
    private final CcpInsertionChainSegmentReplacementGenerator ccpInsertionChainSegmentReplacementGenerator;
    private final CcpInsertionMaximumLengthCalculator ccpInsertionMaximumLengthCalculator;
    private final MinistepBuilder ministepBuilder;
    private final int minSegmentLength;
    private final int maxSegmentLength;
    private final ChainSegmentAlterSelectionProbabilitiesCalculator chainSegmentAlterSelectionProbabilitiesCalculator;
    private final MinistepChainCalculator ministepChainCalculator;

    public CcpInsertionModificationGenerator(UniformDistribution uniform, CcpInsertionModificationBuilder builder,
                                             ChainSegmentReplacementAlterSelectionLogRatioCalculator alterSelectionLogRatioCalculator,
                                             CcpInsertionChainSegmentReplacementGenerator ccpInsertionChainSegmentReplacementGenerator,
                                             CcpInsertionMaximumLengthCalculator ccpInsertionMaximumLengthCalculator,
                                             MinistepBuilder ministepBuilder, int minSegmentLength, int maxSegmentLength,
                                             ChainSegmentAlterSelectionProbabilitiesCalculator chainSegmentAlterSelectionProbabilitiesCalculator,
                                             MinistepChainCalculator ministepChainCalculator) {
        this.uniform = uniform;
        this.builder = builder;
        this.alterSelectionLogRatioCalculator = alterSelectionLogRatioCalculator;
        this.ccpInsertionChainSegmentReplacementGenerator = ccpInsertionChainSegmentReplacementGenerator;
        this.ccpInsertionMaximumLengthCalculator = ccpInsertionMaximumLengthCalculator;
        this.ministepBuilder = ministepBuilder;
        this.minSegmentLength = minSegmentLength;
        this.maxSegmentLength = maxSegmentLength;
        this.chainSegmentAlterSelectionProbabilitiesCalculator = chainSegmentAlterSelectionProbabilitiesCalculator;
        this.ministepChainCalculator = ministepChainCalculator;
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
        ActorAspect startingActorAspect = state.getAt(startingIndex).getActorAspect();
        ActorAspect selectedActorAspect = uniform.nextNotEqualTo(startingActorAspect, state.getActorAspects());
        List<String> alters = ministepChainCalculator.getAltersFor(state, selectedActorAspect);
        String alter = uniform.next(alters);
        Link linkToInsert = ministepBuilder.with()
                .egoAspect(selectedActorAspect)
                .alter(alter)
                .build();
        int adjustedMaxSegmentLength = ccpInsertionMaximumLengthCalculator.calculate(maxSegmentLength, startingIndex, state);
        int segmentLength = uniform.next(minSegmentLength, adjustedMaxSegmentLength);
        int endingIndex = startingIndex + segmentLength + 1;
        ChainSegment replacement = ccpInsertionChainSegmentReplacementGenerator.generate(state, startingIndex, endingIndex, linkToInsert);
        chainSegmentAlterSelectionProbabilitiesCalculator.calculate(replacement.links(), state, model, startingIndex);
        double logAlterSelectionProbability = alterSelectionLogRatioCalculator.calculate(startingIndex, endingIndex-2, replacement, state);
        return builder.with()
                .start(startingIndex)
                .end(endingIndex)
                .replacement(replacement)
                .egoAspect(selectedActorAspect)
                .logAlterSelectionProbability(logAlterSelectionProbability)
                .build();
    }
}
