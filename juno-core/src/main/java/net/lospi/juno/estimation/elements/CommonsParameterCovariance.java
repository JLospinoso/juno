/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.elements;

import net.lospi.juno.model.Effect;
import net.lospi.juno.model.ObjectMatrix;

import java.util.List;

public class CommonsParameterCovariance implements ParameterCovariance {
    private final ObjectMatrix covariance;
    private final List<Effect> effects;

    public CommonsParameterCovariance(ObjectMatrix covariance, List<Effect> effects) {
        this.covariance = covariance;
        this.effects = effects;
    }

    @Override
    public ObjectMatrix getCovariance() {
        return covariance;
    }

    @Override
    public List<Effect> getEffects() {
        return effects;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int rowIndex=0; rowIndex<effects.size(); rowIndex++) {
            if(rowIndex != 0) {
                builder.append(String.format("%n"));
            }
            builder.append("  ");
            for(int columnIndex=0; columnIndex<effects.size(); columnIndex++) {
                builder.append(String.format("%10.3f", covariance.getEntry(rowIndex, columnIndex)));
            }
            builder.append("  ").append(effects.get(rowIndex));
        }
        return builder.toString();
    }
}
