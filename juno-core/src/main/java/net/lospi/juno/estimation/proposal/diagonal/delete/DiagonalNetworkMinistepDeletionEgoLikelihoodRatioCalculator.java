/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.EgoLikelihoodRatioCalculator;
import net.lospi.juno.estimation.proposal.Modification;
import net.lospi.juno.model.Model;
import net.lospi.juno.stat.CachedNaturalLogarithm;

public class DiagonalNetworkMinistepDeletionEgoLikelihoodRatioCalculator implements EgoLikelihoodRatioCalculator {
    private final CachedNaturalLogarithm naturalLogarithm;

    public DiagonalNetworkMinistepDeletionEgoLikelihoodRatioCalculator(CachedNaturalLogarithm naturalLogarithm) {
        this.naturalLogarithm = naturalLogarithm;
    }

    @Override
    public double calculate(Modification modification, Chain state, Model model) {
        return naturalLogarithm.apply(state.getActorAspectCount());
    }
}
