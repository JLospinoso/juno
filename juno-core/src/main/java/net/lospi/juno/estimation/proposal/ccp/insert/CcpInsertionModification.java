/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.insert;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.ChainSegment;
import net.lospi.juno.estimation.proposal.Modification;

public class CcpInsertionModification implements Modification {
    private final Integer start;
    private final Integer end;
    private final Double logAlterSelectionProbability;
    private final boolean identity;
    private final ActorAspect actorAspect;
    private final ChainSegment replacement;

    public CcpInsertionModification(Integer start, Integer end, Double logAlterSelectionProbability,
                                    boolean identity, ChainSegment replacement, ActorAspect actorAspect) {
        this.start = start;
        this.end = end;
        this.logAlterSelectionProbability = logAlterSelectionProbability;
        this.identity = identity;
        this.replacement = replacement;
        this.actorAspect = actorAspect;
    }

    @Override
    public void modify(Chain state) {
        state.replaceFrom(start, end-2, replacement);
    }

    @Override
    public boolean isIdentity() {
        return identity;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public ActorAspect getActorAspect() {
        return actorAspect;
    }

    public double getLogAlterSelectionProbability() {
        return logAlterSelectionProbability;
    }

    public ChainSegment getReplacement() {
        return replacement;
    }
}
