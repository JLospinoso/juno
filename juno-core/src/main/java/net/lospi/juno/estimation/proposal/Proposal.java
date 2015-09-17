/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import net.lospi.juno.elements.Chain;

public class Proposal {
    private final Modification modification;
    private final Double logAcceptanceProbability;

    public Proposal(Modification modification, Double logAcceptanceProbability) {
        this.modification = modification;
        this.logAcceptanceProbability = logAcceptanceProbability;
    }

    public Modification getModification() {
        return modification;
    }

    public Double getLogAcceptanceProbability() {
        return logAcceptanceProbability;
    }

    public Chain update(Chain state) {
        modification.modify(state);
        return state;
    }
}
