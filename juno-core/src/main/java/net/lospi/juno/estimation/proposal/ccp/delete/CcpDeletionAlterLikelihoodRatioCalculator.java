/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.AlterSelectionLikelihoodRatioCalculator;
import net.lospi.juno.model.Model;

public class CcpDeletionAlterLikelihoodRatioCalculator
        implements AlterSelectionLikelihoodRatioCalculator<CcpDeletionModification>{
    @Override
    public double calculate(CcpDeletionModification modification, Chain state, Model model) {
        return modification.getLogAlterSelectionProbability();
    }
}
