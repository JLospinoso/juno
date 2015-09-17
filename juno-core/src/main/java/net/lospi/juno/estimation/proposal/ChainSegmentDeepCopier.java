/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import net.lospi.juno.elements.ChainSegmentBuilder;
import net.lospi.juno.elements.Link;
import net.lospi.juno.estimation.proposal.ChainSegment;

import java.util.ArrayList;
import java.util.List;

public class ChainSegmentDeepCopier {
    private final ChainSegmentBuilder chainSegmentBuilder;

    public ChainSegmentDeepCopier(ChainSegmentBuilder chainSegmentBuilder) {
        this.chainSegmentBuilder = chainSegmentBuilder;
    }

    public ChainSegment copy(ChainSegment segment) {
        List<Link> originals = segment.links();
        List<Link> copies = new ArrayList<Link>();
        for(Link original : originals){
            Link copy = original.deepCopy();
            copies.add(copy);
        }
        return chainSegmentBuilder.with()
                .ministeps(copies)
                .build();
    }
}
