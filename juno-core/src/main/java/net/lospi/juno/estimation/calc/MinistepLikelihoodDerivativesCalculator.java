package net.lospi.juno.estimation.calc;

import net.lospi.juno.elements.Ministep;
import net.lospi.juno.elements.State;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.model.AlterSelectionDistribution;
import net.lospi.juno.model.AlterSelectionStatisticsCalculator;
import net.lospi.juno.model.Model;
import net.lospi.juno.stat.Statistics;

public class MinistepLikelihoodDerivativesCalculator implements LinkLikelihoodDerivativesCalculator<Ministep> {
    private final AlterSelectionStatisticsCalculator alterSelectionStatisticsCalculator;
    private final AlterSelectionDistribution alterSelectionDistribution;

    public MinistepLikelihoodDerivativesCalculator(AlterSelectionStatisticsCalculator alterSelectionStatisticsCalculator,
                                                   AlterSelectionDistribution alterSelectionDistribution) {
        this.alterSelectionStatisticsCalculator = alterSelectionStatisticsCalculator;
        this.alterSelectionDistribution = alterSelectionDistribution;
    }

    @Override
    public void updateLikelihood(Ministep link, State state, Model model) {
        update(link, state, model, false, false);
    }

    @Override
    public void updateLikelihoodAndScore(Ministep link, State state, Model model) {
        update(link, state, model, true, false);
    }

    @Override
    public void updateLikelihoodScoreAndInformation(Ministep link, State state, Model model) {
        update(link, state, model, true, true);
    }

    private void update(Ministep link, State state, Model model, boolean score, boolean information) {
        Statistics statistics = alterSelectionStatisticsCalculator.calculateStatistics(link.getActorAspect(), state, model.getAlterSelectionEffects());
        LikelihoodDerivatives derivatives = alterSelectionDistribution.calculate(statistics, model, link.getAlter(), score, information);
        link.setLikelihoodDerivatives(derivatives);
    }
}
