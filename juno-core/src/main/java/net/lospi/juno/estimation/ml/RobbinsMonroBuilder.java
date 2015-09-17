/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.ml;

public interface RobbinsMonroBuilder {
    RobbinsMonroBuilder with();

    RobbinsMonroBuilder effectCount(int effects);

    RobbinsMonroBuilder phases(int phases);

    RobbinsMonro build();
}
