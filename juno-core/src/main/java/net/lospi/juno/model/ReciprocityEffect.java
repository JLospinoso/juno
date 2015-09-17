/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.model;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.elements.State;

public class ReciprocityEffect implements AlterSelectionEffect {
    private static final int HASH_CODE = ReciprocityEffect.class.hashCode();
    private final String networkName;

    public ReciprocityEffect(String networkName) {
        this.networkName = networkName;
    }

    @Override
    public double statistic(ActorAspect egoAspect, String alter, State state) {
        if(! egoAspect.getAspect().equals(networkName) || egoAspect.getActor().equals(alter)) {
            return 0;
        }
        String ego = egoAspect.getActor();
        boolean egoToAlter = state.getNetwork(networkName).value(ego, alter);
        boolean alterToEgo = state.getNetwork(networkName).value(alter, ego);
        if(egoToAlter && alterToEgo) {
            return -1;
        } else if (alterToEgo) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return String.format("Reciprocity (%s)", networkName);
    }

    @Override
    public int compareTo(Effect o) {
        return toString().compareTo(o.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReciprocityEffect that = (ReciprocityEffect) o;

        if (!networkName.equals(that.networkName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return HASH_CODE;
    }
}
