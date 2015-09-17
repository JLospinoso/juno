/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.permute;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.AlterSelectionLikelihoodRatioCalculator;
import net.lospi.juno.model.Model;

public class PermutationAlterLikelihoodRatioCalculator implements AlterSelectionLikelihoodRatioCalculator<PermutationModification> {
    @Override
    public double calculate(PermutationModification modification, Chain state, Model model) {
        return modification.getLogAlterSelectionProbability();
    }
}
