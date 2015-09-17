/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.mh;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.model.Model;

public interface MetropolisHastingsIterator {
    MetropolisHastingsStep next(Chain state, Model model);
}
