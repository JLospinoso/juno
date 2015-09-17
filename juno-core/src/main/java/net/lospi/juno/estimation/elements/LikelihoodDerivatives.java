/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.elements;

import net.lospi.juno.model.*;

import java.util.List;

public interface LikelihoodDerivatives<T extends Effect> {
    double getLogLikelihood();
    ObjectVector getScore();
    ObjectMatrix getInformation();
    List<T> getEffects();
}
