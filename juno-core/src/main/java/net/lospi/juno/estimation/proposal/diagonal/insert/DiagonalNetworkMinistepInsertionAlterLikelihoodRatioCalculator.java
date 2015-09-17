/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.insert;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.AlterSelectionLikelihoodRatioCalculator;
import net.lospi.juno.model.Model;

public class DiagonalNetworkMinistepInsertionAlterLikelihoodRatioCalculator
        implements AlterSelectionLikelihoodRatioCalculator<DiagonalNetworkMinistepInsertionModification> {
    @Override
    public double calculate(DiagonalNetworkMinistepInsertionModification modification, Chain state, Model model) {
        return modification.getLogAlterSelectionProbability();
    }
}
