/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.stat;

public interface BooleanDistribution {
    boolean nextWithLogProbability(double logAcceptanceProbability);
}
