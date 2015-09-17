/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.model;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.elements.State;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.stat.Statistics;

public class SimpleModelAlterSelectionLogProbabilityCalculator implements AlterSelectionLogProbabilityCalculator {
    private final AlterSelectionStatisticsCalculator alterSelectionStatisticsCalculator;
    private final AlterSelectionDistribution alterSelectionDistribution;

    public SimpleModelAlterSelectionLogProbabilityCalculator(AlterSelectionStatisticsCalculator alterSelectionStatisticsCalculator,
                                                             AlterSelectionDistribution alterSelectionDistribution) {
        this.alterSelectionStatisticsCalculator = alterSelectionStatisticsCalculator;
        this.alterSelectionDistribution = alterSelectionDistribution;
    }

    @Override
    public LikelihoodDerivatives calculate(ActorAspect selectedActorAspect, String alter, Model model, State state) {
        Statistics statistics = alterSelectionStatisticsCalculator.calculateStatistics(selectedActorAspect, state, model.getAlterSelectionEffects());
        return alterSelectionDistribution.calculate(statistics, model, alter, false, false);
    }
}
