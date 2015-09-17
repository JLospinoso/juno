/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.calc;

import net.lospi.juno.elements.Link;
import net.lospi.juno.elements.State;
import net.lospi.juno.model.Model;

public interface LinkLikelihoodDerivativesCalculator<T extends Link> {
    void updateLikelihood(T link, State state, Model model);
    void updateLikelihoodAndScore(T link, State state, Model model);
    void updateLikelihoodScoreAndInformation(T link, State state, Model model);
}
