/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import net.lospi.juno.elements.Link;

public class ChainSegmentAlterSelectionProbabilityCalculator {
    public double calculate(ChainSegment replacement){
        double logProbability = 0;
        for(Link ministep : replacement.links()){
            logProbability += ministep.getLikelihoodDerivatives().getLogLikelihood();
        }
        return logProbability;
    }
}
