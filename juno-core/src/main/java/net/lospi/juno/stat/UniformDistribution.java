/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.stat;

import java.util.List;

public interface UniformDistribution {
    double nextContinuous(double lower, double upper);
    int next(int lower, int upper);
    <T> T next(List<T> items);
    <T> T nextNotEqualTo(T itemToExclude, List<T> items);
}
