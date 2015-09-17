/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.elements;

public interface ObservationBuilder {
    ObservationBuilder with();

    ObservationBuilder observation(Network network);

    ObservationBuilder covariate(ActorCovariate covariate);

    Observation build();
}
