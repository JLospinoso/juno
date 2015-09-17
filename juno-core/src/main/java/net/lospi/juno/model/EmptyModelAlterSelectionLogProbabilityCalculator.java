/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.model;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.elements.Network;
import net.lospi.juno.elements.State;
import net.lospi.juno.estimation.elements.LikelihoodOnlyLikelihoodDerivatives;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.stat.CachedNaturalLogarithm;

public class EmptyModelAlterSelectionLogProbabilityCalculator implements AlterSelectionLogProbabilityCalculator {
    private final CachedNaturalLogarithm naturalLogarithm;

    public EmptyModelAlterSelectionLogProbabilityCalculator(CachedNaturalLogarithm naturalLogarithm) {
        this.naturalLogarithm = naturalLogarithm;
    }

    @Override
    public LikelihoodDerivatives calculate(ActorAspect selectedActorAspect, String actor, Model model, State state) {
        String aspect = selectedActorAspect.getAspect();
        Network network = state.getNetwork(aspect);
        final int alters = network.getActorCount() - 1;
        return new LikelihoodOnlyLikelihoodDerivatives(-1 * naturalLogarithm.apply(alters));
    }
}
