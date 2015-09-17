/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.insert;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.AlterSelectionLikelihoodRatioCalculator;
import net.lospi.juno.model.Model;

public class CcpInsertionAlterLikelihoodRatioCalculator implements AlterSelectionLikelihoodRatioCalculator<CcpInsertionModification> {
    @Override
    public double calculate(CcpInsertionModification modification, Chain state, Model model) {
        return modification.getLogAlterSelectionProbability();
    }
}
