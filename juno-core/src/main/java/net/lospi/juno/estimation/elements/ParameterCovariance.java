/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.elements;

import net.lospi.juno.model.Effect;
import net.lospi.juno.model.ObjectMatrix;

import java.util.List;

public interface ParameterCovariance {
    ObjectMatrix getCovariance();
    List<Effect> getEffects();
}
