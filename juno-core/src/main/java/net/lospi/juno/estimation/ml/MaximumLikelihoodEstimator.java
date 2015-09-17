/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.ml;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.model.Model;

public interface MaximumLikelihoodEstimator {
    MaximumLikelihoodResults estimate(Model model, Chain initialChain, RobbinsMonroPlan plan);
    void setSamplingInterval(int interval);
    void setPhaseOneSample(int phaseOneSample);
    void setPhaseOneBurnIn(int burnIn);
    void setDrawsPerPhaseTwoSample(int drawsPerPhaseTwoSample);
    void setPhaseThreeSamples(int phaseThreeSamples);
}
