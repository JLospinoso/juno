/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.elements;

import com.google.common.base.Objects;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;

public class Ministep implements Link {
    private final String alter;
    private final ActorAspect egoAspect;
    private LikelihoodDerivatives likelihoodDerivatives;

    public Ministep(ActorAspect egoAspect, String alter) {
        this.egoAspect = egoAspect;
        this.alter = alter;
    }

    @Override
    public ActorAspect getActorAspect() {
        return this.egoAspect;
    }

    @Override
    public LikelihoodDerivatives getLikelihoodDerivatives() {
        return likelihoodDerivatives;
    }

    @Override
    public void setLikelihoodDerivatives(LikelihoodDerivatives derivatives) {
        this.likelihoodDerivatives = derivatives;
    }

    @Override
    public void forwardApply(State state) {
        Network network = state.getNetwork(egoAspect.getAspect());
        network.flipTie(egoAspect.getActor(), alter);
    }

    @Override
    public void backwardApply(State state) {
        Network network = state.getNetwork(egoAspect.getAspect());
        network.flipTie(egoAspect.getActor(), alter);
    }

    @Override
    public Link deepCopy() {
        return new Ministep(egoAspect, alter);
    }

    public String getAlter() {
        return alter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ministep ministep = (Ministep) o;
        if (!alter.equals(ministep.alter)) {
            return false;
        }
        return egoAspect.equals(ministep.egoAspect);
    }

    @Override
    public int hashCode() {
        int result = alter.hashCode();
        result = 31 * result + egoAspect.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("egoAspect", egoAspect)
                .add("alter", alter)
                .toString();
    }
}
