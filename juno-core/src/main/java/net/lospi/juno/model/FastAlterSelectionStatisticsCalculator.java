package net.lospi.juno.model;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.elements.Network;
import net.lospi.juno.elements.State;
import net.lospi.juno.stat.CommonsStatistics;
import net.lospi.juno.stat.Statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FastAlterSelectionStatisticsCalculator implements AlterSelectionStatisticsCalculator {
    @Override
    public Statistics calculateStatistics(ActorAspect egoAspect, State state, List<AlterSelectionEffect> effects) {
        Network network = state.getNetwork(egoAspect.getAspect());
        List<String> outcomes = new ArrayList<String>(network.getActors());
        //Collections.sort(outcomes); //TODO: This is not fast. GetActors needs to return a SortedSet / List
        int nEffects = effects.size();
        int nOutcomes = outcomes.size();
        double[][] statisticsMatrix = new double[nOutcomes][nEffects];
        for(int effectIndex=0; effectIndex<nEffects; effectIndex++) {
            for(int actorIndex=0; actorIndex<nOutcomes; actorIndex++) {
                String actor = outcomes.get(actorIndex);
                AlterSelectionEffect effect = effects.get(effectIndex);
                statisticsMatrix[actorIndex][effectIndex] = effect.statistic(egoAspect, actor, state);
            }
        }
        ObjectMatrix statistics = new SafeObjectMatrix(statisticsMatrix, outcomes, effects);
        return new CommonsStatistics(outcomes, effects, statistics);
    }
}
