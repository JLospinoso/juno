package net.lospi.juno.model;

import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.stat.Statistics;

public interface AlterSelectionDistribution {
    LikelihoodDerivatives calculate(Statistics statistics, Model model, String outcome, boolean calculateScore, boolean calculateInformation);
}
