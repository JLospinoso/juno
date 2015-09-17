/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.AlterSelectionLikelihoodRatioCalculator;
import net.lospi.juno.model.Model;

public class DiagonalNetworkMinistepDeletionAlterLikelihoodRatioCalculator
        implements AlterSelectionLikelihoodRatioCalculator<DiagonalNetworkMinistepDeletionModification> {
    @Override
    public double calculate(DiagonalNetworkMinistepDeletionModification modification, Chain state, Model model) {
        return -1D * modification.getLogAlterSelectionProbability();
    }
}
