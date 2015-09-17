package net.lospi.juno.model;

import java.util.ArrayList;
import java.util.List;

public class CommonsModel implements Model {
    private final List<Effect> allEffects;
    private final List<RateEffect> rateEffects;
    private final List<AlterSelectionEffect> alterSelectionEffects;
    private final ObjectVector parameter;

    public CommonsModel(List<RateEffect> rateEffects, List<AlterSelectionEffect> alterSelectionEffects,
                        ObjectVector allEffectsParameter) {
        this.allEffects = new ArrayList<Effect>(rateEffects.size() + alterSelectionEffects.size());
        this.allEffects.addAll(rateEffects);
        this.allEffects.addAll(alterSelectionEffects);
        this.rateEffects = rateEffects;
        this.alterSelectionEffects = alterSelectionEffects;
        this.parameter = allEffectsParameter;
    }

    @Override
    public double globalRate() {
        return parameter.getEntry(0);
    }

    @Override
    public List<Effect> getAllEffects() {
        return allEffects;
    }

    @Override
    public ObjectVector getAllEffectsParameter() {
        return parameter;
    }

    @Override
    public int getAllEffectsSize() {
        return allEffects.size();
    }

    @Override
    public ObjectVector getRateEffectsParameter() {
        return parameter.getSubVector(0,rateEffects.size());
    }

    @Override
    public List<RateEffect> getRateEffects() {
        return rateEffects;
    }

    @Override
    public int getAllRateEffectsSize() {
        return rateEffects.size();
    }

    @Override
    public ObjectVector getAlterSelectionEffectsParameter() {
        return parameter.getSubVector(rateEffects.size(), alterSelectionEffects.size());
    }
    @Override
    public List<AlterSelectionEffect> getAlterSelectionEffects() {
        return alterSelectionEffects;
    }

    @Override
    public int getAllAlterEffectsSize() {
        return alterSelectionEffects.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int i=0; i< getAllEffects().size(); i++) {
            builder.append(String.format("%n  %7.4f %s", getAllEffectsParameter().getEntry(i), getAllEffects().get(i).toString()));
        }
        return builder.toString();
    }
}
