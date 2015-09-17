/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.ml;

import net.lospi.juno.model.Effect;
import net.lospi.juno.model.ObjectMatrix;

import java.util.List;

public interface WeightMatrix {
    ObjectMatrix getWeights();
    List<Effect> getEffects();
}
