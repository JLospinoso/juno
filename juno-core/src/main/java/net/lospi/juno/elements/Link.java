/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.elements;

import net.lospi.juno.estimation.elements.LikelihoodDerivatives;

public interface Link {
    Link deepCopy();
    ActorAspect getActorAspect();
    LikelihoodDerivatives getLikelihoodDerivatives();
    void setLikelihoodDerivatives(LikelihoodDerivatives derivatives);
    void forwardApply(State state);
    void backwardApply(State state);
}
