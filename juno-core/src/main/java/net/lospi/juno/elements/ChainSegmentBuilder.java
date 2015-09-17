/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.elements;

import net.lospi.juno.estimation.proposal.ChainSegment;

import java.util.List;

public class ChainSegmentBuilder {
    public Stub with() {
        return new Stub();
    }
    public class Stub {
        List<Link> links;

        public Stub ministeps(List<Link> links) {
            this.links = links;
            return this;
        }

        public ChainSegment build() {
            if(links==null){
                throw new IllegalArgumentException("You must set the links.");
            }
            return new ChainSegment(links);
        }
    }
}
