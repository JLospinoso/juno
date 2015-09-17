/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.util;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.Observation;

public interface ChainBuilder {
    Chain build(Observation... observations);
}
