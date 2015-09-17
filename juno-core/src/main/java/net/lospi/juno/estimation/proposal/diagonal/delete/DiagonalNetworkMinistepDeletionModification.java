/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.Modification;

public class DiagonalNetworkMinistepDeletionModification implements Modification {
    private final Integer deletionIndex;
    private final Double logAlterSelectionProbability;
    private final boolean identity;

    public DiagonalNetworkMinistepDeletionModification(Integer deletionIndex, Double logAlterSelectionProbability, boolean identity) {
        this.deletionIndex = deletionIndex;
        this.logAlterSelectionProbability = logAlterSelectionProbability;
        this.identity = identity;
    }

    public int getDeletionIndex() {
        return deletionIndex;
    }

    public double getLogAlterSelectionProbability() {
        return logAlterSelectionProbability;
    }

    @Override
    public void modify(Chain state) {
        state.deleteAt(deletionIndex);
    }

    @Override
    public boolean isIdentity() {
        return identity;
    }
}
