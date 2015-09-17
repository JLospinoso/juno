/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.permute;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.KappaFactorCalculator;
import net.lospi.juno.estimation.proposal.Modification;
import net.lospi.juno.model.Model;

public class PermutationKappaFactorCalculator implements KappaFactorCalculator {

    @Override
    public double calculate(Model model, Chain state, Modification modification) {
        return 0;
    }
}
