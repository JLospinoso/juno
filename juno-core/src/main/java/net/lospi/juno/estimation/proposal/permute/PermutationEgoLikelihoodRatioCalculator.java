/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.permute;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.EgoLikelihoodRatioCalculator;
import net.lospi.juno.estimation.proposal.Modification;
import net.lospi.juno.model.Model;

public class PermutationEgoLikelihoodRatioCalculator implements EgoLikelihoodRatioCalculator {
    @Override
    public double calculate(Modification modification, Chain state, Model model) {
        return 0;
    }
}
