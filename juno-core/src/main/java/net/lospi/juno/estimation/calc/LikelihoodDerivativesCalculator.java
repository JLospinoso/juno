/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.calc;

import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.model.Effect;

import java.util.List;

public interface LikelihoodDerivativesCalculator<T extends Effect> {
    LikelihoodDerivatives calculate(List<LikelihoodDerivatives> completeDataDerivatives, List<T> effects);
}
