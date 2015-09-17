package net.lospi.juno.stat;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.model.Effect;
import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.ObjectVector;
import net.lospi.juno.model.SafeObjectMatrix;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class CommonsStatisticsTest {
    private ObjectMatrix statistics;
    private String outcome1, outcome2, outcome3;
    private Effect effect1, effect2;
    private List<Effect> effects;
    private List<String> outcomes;

    @BeforeMethod
    public void setUp() {
        outcome1 = "1";
        outcome2 = "2";
        outcome3 = "3";
        outcomes = ImmutableList.of(outcome1, outcome2, outcome3);
        effect1 = mock(Effect.class);
        effect2 = mock(Effect.class);
        effects = ImmutableList.of(effect1, effect2);
        statistics = new SafeObjectMatrix( new double[][] {
                        {1d, 2d},
                        {3d, 4d},
                        {5d, 6d},
                }, outcomes, effects);
    }

    public void indexOfEffect() {
        CommonsStatistics underStudy = new CommonsStatistics(outcomes, effects, statistics);
        int result = underStudy.indexOfEffect(effect2);
        assertThat(result).isEqualTo(1);
    }

    public void getEffects() {
        CommonsStatistics underStudy = new CommonsStatistics(outcomes, effects, statistics);
        List result = underStudy.getEffects();
        assertThat(result).isEqualTo(effects);
    }

    public void getRowsAsOutcomes() {
        CommonsStatistics underStudy = new CommonsStatistics(outcomes, effects, statistics);
        ObjectMatrix result = underStudy.getRowsAsOutcomes();
        assertThat(result).isEqualTo(statistics);
    }

    public void getByOutcome() {
        CommonsStatistics underStudy = new CommonsStatistics(outcomes, effects, statistics);
        ObjectVector result = underStudy.getByOutcome(outcome2);
        assertThat(result.getEntry(0)).isEqualTo(3);
        assertThat(result.getEntry(1)).isEqualTo(4);
    }

    public void getOutcomes() {
        CommonsStatistics underStudy = new CommonsStatistics(outcomes, effects, statistics);
        List result = underStudy.getOutcomes();
        assertThat(result).isEqualTo(outcomes);
    }

    public void getByEffect() {
        CommonsStatistics underStudy = new CommonsStatistics(outcomes, effects, statistics);
        ObjectVector result = underStudy.getByEffect(effect2);
        assertThat(result.getEntry(0)).isEqualTo(2);
        assertThat(result.getEntry(1)).isEqualTo(4);
        assertThat(result.getEntry(2)).isEqualTo(6);
    }

    public void indexOfOutcome() {
        CommonsStatistics underStudy = new CommonsStatistics(outcomes, effects, statistics);
        int result = underStudy.indexOfOutcome("3");
        assertThat(result).isEqualTo(2);
    }
}