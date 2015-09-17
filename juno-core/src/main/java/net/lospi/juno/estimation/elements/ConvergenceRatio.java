package net.lospi.juno.estimation.elements;

import net.lospi.juno.model.Effect;
import net.lospi.juno.model.ObjectVector;

import java.util.List;

public class ConvergenceRatio {
    private final ObjectVector tRatios;
    private final List<Effect> effects;

    public ConvergenceRatio(ObjectVector tRatios, List<Effect> effects) {
        this.tRatios = tRatios;
        this.effects = effects;
    }

    public ObjectVector getTRatios() {
        return tRatios;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int effectIndex=0; effectIndex<effects.size(); effectIndex++) {
            if(effectIndex != 0) {
                builder.append(String.format("%n"));
            }
            builder.append(String.format("  %10.3f %s", tRatios.getEntry(effectIndex), effects.get(effectIndex)));
        }
        return builder.toString();
    }
}
