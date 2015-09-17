package net.lospi.juno.model;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.stat.CachedExponential;
import net.lospi.juno.stat.CachedNaturalLogarithm;
import net.lospi.juno.stat.CommonsStatistics;
import net.lospi.juno.stat.Statistics;
import org.fest.assertions.data.Offset;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.offset;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//TODO: Check the numbers. This is only a regression test
@Test(groups="unit")
public class CommonsAlterSelectionDistributionTest {
    private Statistics statistics;
    private String observation, outcome2, outcome3;
    private AlterSelectionEffect effect1, effect2;
    private Model model;
    private ObjectMatrix statisticsMatrix;
    private ObjectVector parameter;
    private Offset<Double> tolerance;
    private List<String> outcomes;
    private CachedNaturalLogarithm naturalLogarithm;
    private CachedExponential exponential;
    private List<AlterSelectionEffect> effects;

    @BeforeMethod
    public void setUp() {
        naturalLogarithm = new CachedNaturalLogarithm();
        exponential = new CachedExponential();
        tolerance = offset(1e-15);
        observation = "a";
        outcome2 = "b";
        outcome3 = "c";
        effect1 = mock(AlterSelectionEffect.class);
        effect2 = mock(AlterSelectionEffect.class);
        outcomes = ImmutableList.of(observation, outcome2, outcome3);
        effects = ImmutableList.of(effect1, effect2);
        statisticsMatrix = new SafeObjectMatrix(new double[][] {
                { 1D, 2D },
                { 3D, 4D },
                { 5D, 6D },
        }, outcomes, effects);
        parameter = new SafeObjectVector(new double[] { -1, -2 }, effects);
        statistics = new CommonsStatistics(outcomes, effects, statisticsMatrix);
        model = mock(Model.class);
        when(model.getAlterSelectionEffectsParameter()).thenReturn(parameter);
        when(model.getAlterSelectionEffects()).thenReturn(effects);
    }

    public void calculateLikelihoodOnly() {
        CommonsAlterSelectionDistribution underStudy = new CommonsAlterSelectionDistribution(naturalLogarithm, exponential);
        LikelihoodDerivatives result = underStudy.calculate(statistics, model, observation, false, false);
        assertThat(result.getLogLikelihood()).isEqualTo(-0.0024818141389797432, tolerance);
        assertThat(result.getScore()).isNull();
        assertThat(result.getInformation()).isNull();
    }

    public void calculateScore() {
        CommonsAlterSelectionDistribution underStudy = new CommonsAlterSelectionDistribution(naturalLogarithm, exponential);
        LikelihoodDerivatives result = underStudy.calculate(statistics, model, observation, true, false);
        assertThat(result.getLogLikelihood()).isEqualTo(-0.0024818141389797432, tolerance);
        assertThat(result.getScore().getEntry(0)).isEqualTo(-0.00496973193380934, tolerance);
        assertThat(result.getScore().getEntry(1)).isEqualTo(-0.004969731933809118, tolerance);
        assertThat(result.getInformation()).isNull();
    }

    public void calculateInformation() {
        CommonsAlterSelectionDistribution underStudy = new CommonsAlterSelectionDistribution(naturalLogarithm, exponential);
        LikelihoodDerivatives result = underStudy.calculate(statistics, model, observation, true, true);
        assertThat(result.getLogLikelihood()).isEqualTo(-0.0024818141389797432, tolerance);
        assertThat(result.getScore().getEntry(0)).isEqualTo(-0.00496973193380934, tolerance);
        assertThat(result.getScore().getEntry(1)).isEqualTo(-0.004969731933809118, tolerance);
        assertThat(result.getInformation().getEntry(0,0)).isEqualTo(0.00996379749186012, tolerance);
        assertThat(result.getInformation().getEntry(1,0)).isEqualTo(0.00996379749186012, tolerance);
        assertThat(result.getInformation().getEntry(0,1)).isEqualTo(0.00996379749186012, tolerance);
        assertThat(result.getInformation().getEntry(1,1)).isEqualTo(0.00996379749186012, tolerance);
    }
}
