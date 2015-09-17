package net.lospi.juno.model;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.elements.State;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.stat.Statistics;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class SimpleModelAlterSelectionLogProbabilityCalculatorTest {
    private State state;
    private ActorAspect selectedActorAspect;
    private AlterSelectionStatisticsCalculator alterSelectionStatisticsCalculator;
    private String alter;
    private Model model;
    private AlterSelectionDistribution alterSelectionDistribution;
    private Statistics statistics;
    private LikelihoodDerivatives expected;
    private List<AlterSelectionEffect> effects;

    @BeforeMethod
    public void setUp() {
        effects = mock(List.class);
        alter = "alter";
        alterSelectionDistribution = mock(AlterSelectionDistribution.class);
        selectedActorAspect = mock(ActorAspect.class);
        model = mock(Model.class);
        alterSelectionStatisticsCalculator = mock(AlterSelectionStatisticsCalculator.class);
        state = mock(State.class);
        statistics = mock(Statistics.class);
        expected = mock(LikelihoodDerivatives.class);

        when(model.getAlterSelectionEffects()).thenReturn(effects);
        when(alterSelectionStatisticsCalculator.calculateStatistics(selectedActorAspect, state, effects)).thenReturn(statistics);
        when(alterSelectionDistribution.calculate(statistics, model, alter, false, false)).thenReturn(expected);
    }

    public void calculate() {
        SimpleModelAlterSelectionLogProbabilityCalculator underStudy = new SimpleModelAlterSelectionLogProbabilityCalculator(alterSelectionStatisticsCalculator, alterSelectionDistribution);
        LikelihoodDerivatives result = underStudy.calculate(selectedActorAspect, alter, model, state);
        assertThat(result).isEqualTo(expected);
    }

}
