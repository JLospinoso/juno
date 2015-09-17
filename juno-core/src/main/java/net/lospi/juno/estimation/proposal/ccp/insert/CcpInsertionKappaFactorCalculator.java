/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.insert;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.KappaFactorCalculator;
import net.lospi.juno.estimation.proposal.Modification;
import net.lospi.juno.model.Model;
import net.lospi.juno.stat.CachedNaturalLogarithm;

public class CcpInsertionKappaFactorCalculator implements KappaFactorCalculator {
    private final CachedNaturalLogarithm naturalLogarithm;

    public CcpInsertionKappaFactorCalculator(CachedNaturalLogarithm naturalLogarithm) {
        this.naturalLogarithm = naturalLogarithm;
    }

    @Override
    public double calculate(Model model, Chain state, Modification modification) {
        return 2D * naturalLogarithm.apply(model.globalRate())
            - naturalLogarithm.apply(state.getSize() + 1)
            - naturalLogarithm.apply(state.getSize() + 2);
    }
}
