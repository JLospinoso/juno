package net.lospi.juno.estimation.ml;

import net.lospi.juno.model.Effect;
import net.lospi.juno.model.ObjectMatrix;

import java.util.List;

public class SimpleWeightMatrix implements WeightMatrix {
    private final ObjectMatrix weights;
    private final List<Effect> effects;

    public SimpleWeightMatrix(ObjectMatrix weights, List<Effect> effects) {
        this.weights = weights;
        this.effects = effects;
    }

    @Override
    public ObjectMatrix getWeights() {
        return weights;
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
                builder.append(String.format("%10.3f", weights.getEntry(rowIndex, columnIndex)));
            }
            builder.append("  ").append(effects.get(rowIndex));
        }
        return builder.toString();
    }

    @Override
    public List<Effect> getEffects() {
        return effects;
    }
}


