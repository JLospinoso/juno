/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.insert;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.Ministep;
import net.lospi.juno.estimation.proposal.Modification;

public class DiagonalNetworkMinistepInsertionModification implements Modification {
    private final Integer insertionIndex;
    private final Ministep diagonalMinistep;
    private final Double logAlterSelectionProbability;
    private final boolean identity;

    public DiagonalNetworkMinistepInsertionModification(Integer insertionIndex, Ministep diagonalMinistep,
                                                        Double logAlterSelectionProbability, boolean identity) {
        this.diagonalMinistep = diagonalMinistep;
        this.insertionIndex = insertionIndex;
        this.logAlterSelectionProbability = logAlterSelectionProbability;
        this.identity = identity;
    }

    public int getInsertionIndex() {
        return insertionIndex;
    }

    @Override
    public void modify(Chain state) {
        state.insertAt(insertionIndex, diagonalMinistep);
    }

    @Override
    public boolean isIdentity() {
        return this.identity;
    }

    public double getLogAlterSelectionProbability() {
        return logAlterSelectionProbability;
    }

    public Ministep getMinistep() {
        return diagonalMinistep;
    }
}
