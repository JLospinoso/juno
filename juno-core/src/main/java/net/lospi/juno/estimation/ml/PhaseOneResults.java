/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.ml;

public interface PhaseOneResults {
    WeightMatrix getWeightMatrix();
    boolean isSuccessful();
    String getStatus();
}
