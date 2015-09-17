/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.insert;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.Link;
import net.lospi.juno.estimation.proposal.ChainSegment;
import net.lospi.juno.estimation.proposal.ChainSegmentDeepCopier;

public class CcpInsertionChainSegmentReplacementGenerator {
    private final ChainSegmentDeepCopier chainSegmentDeepCopier;

    public CcpInsertionChainSegmentReplacementGenerator(ChainSegmentDeepCopier chainSegmentDeepCopier) {
        this.chainSegmentDeepCopier = chainSegmentDeepCopier;
    }

    public ChainSegment generate(Chain state, int startingIndex, int endingIndex, Link linkToInsert) {
        if(startingIndex + 2 > endingIndex){
            throw new IllegalArgumentException("Your indices must preserve ordering (end + 2 >= start).");
        }
        ChainSegment shallowCopy = state.segment(startingIndex, endingIndex-2);
        ChainSegment deepCopy = chainSegmentDeepCopier.copy(shallowCopy);
        Link prependDeepCopy = linkToInsert.deepCopy();
        Link appendDeepCopy = linkToInsert.deepCopy();
        deepCopy.prepend(prependDeepCopy);
        deepCopy.append(appendDeepCopy);
        return deepCopy;
    }
}
