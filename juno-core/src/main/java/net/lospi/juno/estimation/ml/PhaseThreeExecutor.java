/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.ml;

import net.lospi.juno.model.Model;

public interface PhaseThreeExecutor {
    PhaseThreeResults execute();
    void setPhaseThreeIterations(int phaseThreeSamples);
    void setSamplingInterval(int samplingInterval);
    void setModel(Model model);
}
