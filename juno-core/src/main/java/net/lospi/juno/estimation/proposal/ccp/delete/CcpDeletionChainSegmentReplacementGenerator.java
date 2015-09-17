/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.ChainSegment;
import net.lospi.juno.estimation.proposal.ChainSegmentDeepCopier;

public class CcpDeletionChainSegmentReplacementGenerator {
    private final ChainSegmentDeepCopier chainSegmentDeepCopier;

    public CcpDeletionChainSegmentReplacementGenerator(ChainSegmentDeepCopier chainSegmentDeepCopier) {
        this.chainSegmentDeepCopier = chainSegmentDeepCopier;
    }

    public ChainSegment generate(Chain state, int segmentStart, int segmentEnd) {
        int replacementStart = segmentStart + 1;
        int replacementEnd = segmentEnd-1;
        if(segmentStart >= segmentEnd){
            throw new IllegalArgumentException("Your indices must preserve ordering (end > start).");
        }
        ChainSegment shallowCopy = state.segment(replacementStart, replacementEnd);
        return chainSegmentDeepCopier.copy(shallowCopy);
    }
}
