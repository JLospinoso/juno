/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.elements;

import com.google.common.base.Objects;

public class ActorAspect {
    private final String actor;
    private final String aspect;

    public ActorAspect(String actor, String aspect) {
        this.actor = actor;
        this.aspect = aspect;
    }

    public String getActor() {
        return actor;
    }

    public String getAspect() {
        return aspect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActorAspect that = (ActorAspect) o;

        if (!actor.equals(that.actor)) {
            return false;
        }
        return aspect.equals(that.aspect);

    }

    @Override
    public int hashCode() {
        int result = actor.hashCode();
        result = 31 * result + aspect.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("actor", actor)
                .add("aspect", aspect)
                .toString();
    }
}
