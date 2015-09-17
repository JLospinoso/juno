package net.lospi.juno.model;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.elements.Network;
import net.lospi.juno.elements.State;
import net.lospi.juno.stat.CommonsStatisticsBuilder;
import net.lospi.juno.stat.Statistics;

import java.util.List;

public class CommonsAlterSelectionStatisticsCalculator implements AlterSelectionStatisticsCalculator {
    private final CommonsStatisticsBuilder<AlterSelectionEffect> builder;

    public CommonsAlterSelectionStatisticsCalculator(CommonsStatisticsBuilder<AlterSelectionEffect> builder) {
        this.builder = builder;
    }

    @Override
    public Statistics calculateStatistics(ActorAspect egoAspect, State state, List<AlterSelectionEffect> effects) {
        CommonsStatisticsBuilder.Stub resultStub = builder.with().effectsOrder(effects);
        Network network = state.getNetwork(egoAspect.getAspect());
        for(AlterSelectionEffect effect : effects) {
            for(String actor : network.getActors()) {
                double statistic = effect.statistic(egoAspect, actor, state);
                resultStub.outcome(effect, actor, statistic);
            }
        }
        return resultStub.build();
    }
}
