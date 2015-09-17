/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.model;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.elements.State;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;

public interface AlterSelectionLogProbabilityCalculator {
    LikelihoodDerivatives calculate(ActorAspect selectedActorAspect, String actor, Model model, State state);
}
