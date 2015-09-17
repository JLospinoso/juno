package net.lospi.juno.model;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.elements.State;

public interface AlterSelectionEffect extends Effect {
    double statistic(ActorAspect egoAspect, String actor, State state);
}
