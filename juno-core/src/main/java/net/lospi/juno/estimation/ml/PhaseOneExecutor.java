/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.ml;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.model.Model;

public interface PhaseOneExecutor {
    PhaseOneResults execute(Model model, Chain initialChain);
    void setBurnIn(int phaseOneBurnIn);
    void setSamples(int phaseOneSamples);
    void setSamplingInterval(int samplingInterval);
}
