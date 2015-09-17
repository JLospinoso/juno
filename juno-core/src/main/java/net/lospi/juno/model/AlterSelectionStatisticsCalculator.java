package net.lospi.juno.model;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.elements.State;
import net.lospi.juno.stat.Statistics;

import java.util.List;

public interface AlterSelectionStatisticsCalculator {
    Statistics calculateStatistics(ActorAspect egoAspect,  State state, List<AlterSelectionEffect> effects);
}
