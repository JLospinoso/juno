/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import net.lospi.juno.elements.Chain;

public interface Modification {
    void modify(Chain state);
    boolean isIdentity();
}
