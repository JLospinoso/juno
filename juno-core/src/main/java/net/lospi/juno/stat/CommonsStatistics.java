package net.lospi.juno.stat;

import net.lospi.juno.model.Effect;
import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.ObjectVector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonsStatistics<T extends Effect> implements Statistics {
    private final Map<String, Integer> outcomesMap;
    private final Map<T, Integer> effectsMap;
    private final List<String> outcomes;
    private final List<T> effects;
    private final ObjectMatrix statistics;

    public CommonsStatistics(List<String> outcomes, List<T> effects, ObjectMatrix statistics) {
        this.outcomes = outcomes;
        this.effects = effects;
        this.statistics = statistics;

        effectsMap = makeMap(effects);
        outcomesMap = makeMap(outcomes);
    }

    @Override
    public List<T> getEffects() {
        return effects;
    }

    @Override
    public List<String> getOutcomes() {
        return outcomes;
    }

    @Override
    public ObjectVector getByOutcome(String outcome) {
        int index = outcomesMap.get(outcome);
        return statistics.getRowVector(index);
    }

    @Override
    public ObjectVector getByEffect(Effect effect) {
        int index = effectsMap.get(effect);
        return statistics.getColumnVector(index);
    }

    @Override
    public ObjectMatrix getRowsAsOutcomes() {
        return statistics;
    }

    @Override
    public int indexOfEffect(Effect effect) {
        return effectsMap.get(effect);
    }

    @Override
    public int indexOfOutcome(String outcome) {
        return outcomesMap.get(outcome);
    }

    private <T> Map<T,Integer> makeMap(List<T> list) {
        Map<T, Integer> result = new HashMap<T, Integer>(list.size());
        int i=0;
        for(T element : list) {
            result.put(element, i++);
        }
        return result;
    }
}
