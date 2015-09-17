/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.sim;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.model.Model;

public interface ChainSimulator {
    void advance();
    void setChain(Chain chain);
    void setModel(Model model);
    Chain getChain();
    boolean isChainSet();
}
