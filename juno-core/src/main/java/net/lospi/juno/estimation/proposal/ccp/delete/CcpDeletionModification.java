/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.ChainSegment;
import net.lospi.juno.estimation.proposal.Modification;

public class CcpDeletionModification implements Modification {
    private final Integer start;
    private final Integer end;
    private final Double logAlterSelectionProbability;
    private final boolean identity;
    private final ChainSegment replacement;

    public CcpDeletionModification(Integer start, Integer end, Double logAlterSelectionProbability, boolean identity, ChainSegment replacement) {
        this.start = start;
        this.end = end;
        this.logAlterSelectionProbability = logAlterSelectionProbability;
        this.identity = identity;
        this.replacement = replacement;
    }

    @Override
    public void modify(Chain state) {
        state.deleteAt(end);
        state.deleteAt(start);
    }

    @Override
    public boolean isIdentity() {
        return identity;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }

    public double getLogAlterSelectionProbability() {
        return logAlterSelectionProbability;
    }

    public ChainSegment getReplacement() {
        return replacement;
    }
}
