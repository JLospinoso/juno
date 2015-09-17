/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.sim.ParametricProcess;
import net.lospi.juno.model.ObjectMatrix;

public interface RobbinsMonro {
    RobbinsMonroResult optimize(ParametricProcess simulator, ObjectMatrix weight, RobbinsMonroPlan plan);
}