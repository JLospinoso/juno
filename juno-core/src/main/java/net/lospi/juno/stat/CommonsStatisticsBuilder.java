package net.lospi.juno.stat;

import com.google.common.collect.Lists;
import net.lospi.juno.model.Effect;
import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.SafeObjectMatrix;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonsStatisticsBuilder<T extends Effect> {
    public Stub with() {
        return new Stub();
    }
    public class Stub {
        private Map<String, Map<T, Double>> statisticsMap = new HashMap<String, Map<T, Double>>();
        private List<T> effectList;

        public Stub effectsOrder(List<T> effectList) {
            this.effectList = effectList;
            return this;
        }

        public Stub outcome(T effect, String outcome, double value) {
            statisticsMap.putIfAbsent(outcome, new HashMap<T, Double>());
            Map<T, Double> effectMap = statisticsMap.get(outcome);
            effectMap.put(effect, value);
            return this;
        }

        public Statistics build() {
            if(statisticsMap.isEmpty() || effectList == null || effectList.isEmpty()){
                throw new IllegalStateException("You must set at least one effect.");
            }
            int numberOfEffects = effectList.size();
            List<String> outcomes = Lists.newArrayList(statisticsMap.keySet());
            Collections.sort(outcomes);
            double[][] statisticsArray = new double[outcomes.size()][effectList.size()];
            for(int outcomeIndex=0; outcomeIndex<outcomes.size(); outcomeIndex++) {
                String outcome = outcomes.get(outcomeIndex);
                Map<T, Double> statisticsForOutcome = this.statisticsMap.get(outcome);
                for(int effectIndex=0; effectIndex<numberOfEffects; effectIndex++) {
                    Effect effect = effectList.get(effectIndex);
                    double value = statisticsForOutcome.get(effect);
                    statisticsArray[outcomeIndex][effectIndex] = value;
                }
            }
            ObjectMatrix statistics = new SafeObjectMatrix(statisticsArray, outcomes, effectList);
            return new CommonsStatistics(outcomes, effectList, statistics);
        }
    }
}
