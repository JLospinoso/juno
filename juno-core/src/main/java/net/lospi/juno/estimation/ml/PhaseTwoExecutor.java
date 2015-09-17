/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.ml;

import net.lospi.juno.model.Model;

public interface PhaseTwoExecutor {
    PhaseTwoResults execute(Model model, WeightMatrix weightMatrix, RobbinsMonroPlan plan);
    void setSamplingInterval(int samplingInterval);
    void setDrawsPerSample(int drawsPerSample);
}
