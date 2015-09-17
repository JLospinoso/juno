/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.insert;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.EgoLikelihoodRatioCalculator;
import net.lospi.juno.estimation.proposal.Modification;
import net.lospi.juno.model.Model;
import net.lospi.juno.stat.CachedNaturalLogarithm;

public class DiagonalNetworkMinistepInsertionEgoLikelihoodRatioCalculator implements EgoLikelihoodRatioCalculator {
    private final CachedNaturalLogarithm naturalLogarithm;

    public DiagonalNetworkMinistepInsertionEgoLikelihoodRatioCalculator(CachedNaturalLogarithm naturalLogarithm) {
        this.naturalLogarithm = naturalLogarithm;
    }

    @Override
    public double calculate(Modification modification, Chain state, Model model) {
        return -1 * naturalLogarithm.apply(state.getActorAspectCount());
    }
}
