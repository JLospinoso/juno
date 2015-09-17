/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.model;

import java.util.List;

public interface Model {
    double globalRate();//todo: GET RID OF THIS!

    ObjectVector getAllEffectsParameter();
    List<Effect> getAllEffects();
    int getAllEffectsSize();

    ObjectVector getRateEffectsParameter();
    List<RateEffect> getRateEffects();
    int getAllRateEffectsSize();

    ObjectVector getAlterSelectionEffectsParameter();
    List<AlterSelectionEffect> getAlterSelectionEffects();
    int getAllAlterEffectsSize();
}
