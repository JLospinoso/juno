package net.lospi.juno.estimation.calc;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.model.*;
import net.lospi.juno.stat.Statistics;
import org.testng.annotations.BeforeMethod;
import net.lospi.juno.elements.State;
import net.lospi.juno.elements.Ministep;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

import java.util.List;

@Test(groups="unit")
public class MinistepLikelihoodDerivativesCalculatorTest {
    private State state;
    private Ministep link;
    private AlterSelectionStatisticsCalculator alterSelectionStatisticsCalculator;
    private Model model;
    private AlterSelectionDistribution alterSelectionDistribution;
    private String outcome;
    private ActorAspect actorAspect;
    private LikelihoodDerivatives derivatives;
    private Statistics statistics;
    private List<AlterSelectionEffect> effects;

    @BeforeMethod
    public void setUp() {
        effects = mock(List.class);
        outcome = "a";
        derivatives = mock(LikelihoodDerivatives.class);
        actorAspect = mock(ActorAspect.class);
        link = mock(Ministep.class);
        alterSelectionDistribution = mock(AlterSelectionDistribution.class);
        model = mock(Model.class);
        alterSelectionStatisticsCalculator = mock(AlterSelectionStatisticsCalculator.class);
        state = mock(State.class);
        statistics = mock(Statistics.class);

        when(model.getAlterSelectionEffects()).thenReturn(effects);
        when(link.getActorAspect()).thenReturn(actorAspect);
        when(link.getAlter()).thenReturn(outcome);
        when(alterSelectionStatisticsCalculator.calculateStatistics(actorAspect, state, effects)).thenReturn(statistics);
    }

    public void updateLikelihoodScoreAndInformation() {
        MinistepLikelihoodDerivativesCalculator underStudy = new MinistepLikelihoodDerivativesCalculator(alterSelectionStatisticsCalculator, alterSelectionDistribution);
        when(alterSelectionDistribution.calculate(statistics, model, outcome, true, true)).thenReturn(derivatives);
        underStudy.updateLikelihoodScoreAndInformation(link, state, model);
        verify(link).setLikelihoodDerivatives(derivatives);
    }

    public void updateLikelihood() {
        MinistepLikelihoodDerivativesCalculator underStudy = new MinistepLikelihoodDerivativesCalculator(alterSelectionStatisticsCalculator, alterSelectionDistribution);
        when(alterSelectionDistribution.calculate(statistics, model, outcome, false, false)).thenReturn(derivatives);
        underStudy.updateLikelihood(link, state, model);
        verify(link).setLikelihoodDerivatives(derivatives);
    }

    public void updateLikelihoodAndScore() {
        MinistepLikelihoodDerivativesCalculator underStudy = new MinistepLikelihoodDerivativesCalculator(alterSelectionStatisticsCalculator, alterSelectionDistribution);
        when(alterSelectionDistribution.calculate(statistics, model, outcome, true, false)).thenReturn(derivatives);
        underStudy.updateLikelihoodAndScore(link, state, model);
        verify(link).setLikelihoodDerivatives(derivatives);
    }

}
