/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.permute;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.ChainSegment;
import net.lospi.juno.estimation.proposal.ChainSegmentDeepCopier;

public class PermutationChainSegmentReplacementGenerator {
    private final ChainSegmentDeepCopier chainSegmentDeepCopier;

    public PermutationChainSegmentReplacementGenerator(ChainSegmentDeepCopier chainSegmentDeepCopier) {
        this.chainSegmentDeepCopier = chainSegmentDeepCopier;
    }

    public ChainSegment generate(Chain state, int startingIndex, int endingIndex) {
        if(startingIndex > endingIndex){
            throw new IllegalArgumentException("Your indices must preserve ordering (end >= start).");
        }
        ChainSegment shallowCopy = state.segment(startingIndex, endingIndex);
        return chainSegmentDeepCopier.copy(shallowCopy);
    }
}
