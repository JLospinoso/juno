/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import net.lospi.juno.elements.*;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.model.AlterSelectionLogProbabilityCalculator;
import net.lospi.juno.model.Model;

import java.util.List;

public class ChainSegmentAlterSelectionProbabilitiesCalculator {
    private final AlterSelectionLogProbabilityCalculator alterSelectionLogProbabilityCalculator;

    public ChainSegmentAlterSelectionProbabilitiesCalculator(AlterSelectionLogProbabilityCalculator alterSelectionLogProbabilityCalculator) {
        this.alterSelectionLogProbabilityCalculator = alterSelectionLogProbabilityCalculator;
    }

    public void calculate(List<Link> links, Chain chain, Model model, int startingIndex) {
        State state = chain.stateAt(startingIndex - 1);
        for(Link link : links) {
            Ministep ministep = (Ministep) link;
            ActorAspect actorAspect = ministep.getActorAspect();
            String alter = ministep.getAlter();
            LikelihoodDerivatives likelihoodDerivatives = alterSelectionLogProbabilityCalculator.calculate(actorAspect, alter, model, state);
            ministep.setLikelihoodDerivatives(likelihoodDerivatives);
            link.forwardApply(state);
        }
        for(int i=links.size()-1; i>=0; i--) {
            links.get(i).backwardApply(state);
        }
    }
}
