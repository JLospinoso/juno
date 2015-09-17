/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.stat;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.model.Effect;
import net.lospi.juno.model.ObjectMatrix;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class CommonsStatisticsBuilderTest {
    private Effect effects1, effects2;
    private List<Effect> effectList;

    @BeforeMethod
    public void setUp(){
        effects1 = mock(Effect.class);
        effects2 = mock(Effect.class);
        effectList = ImmutableList.of(effects1, effects2);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void throwWhenEffectsNotSet() throws Exception {
        CommonsStatisticsBuilder builder = new CommonsStatisticsBuilder();
        builder.with()
                .outcome(effects2, "1", 1)
                .build();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void throwWhenEffectsEmpty() throws Exception {
        CommonsStatisticsBuilder builder = new CommonsStatisticsBuilder();
        builder.with()
                .effectsOrder(new ArrayList<Effect>())
                .outcome(effects2, "1", 1)
                .build();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void throwWhenStatisticsNotSet() throws Exception {
        CommonsStatisticsBuilder builder = new CommonsStatisticsBuilder();
        builder.with()
                .effectsOrder(effectList)
                .build();
    }

    public void containsOutcome() throws Exception {
        CommonsStatisticsBuilder builder = new CommonsStatisticsBuilder();
        Statistics result = builder.with()
                .effectsOrder(effectList)
                .outcome(effects2, "1", 1)
                .outcome(effects2, "2", 2)
                .outcome(effects2, "3", 3)
                .outcome(effects1, "1", 4)
                .outcome(effects1, "2", 5)
                .outcome(effects1, "3", 6)
                .build();
        assertThat(result.getOutcomes()).containsOnly("1", "2", "3");
    }

    public void effectsAreInOrder() throws Exception {
        CommonsStatisticsBuilder builder = new CommonsStatisticsBuilder();
        Statistics result = builder.with()
                .effectsOrder(effectList)
                .outcome(effects2, "1", 1)
                .outcome(effects2, "2", 2)
                .outcome(effects2, "3", 3)
                .outcome(effects1, "1", 4)
                .outcome(effects1, "2", 5)
                .outcome(effects1, "3", 6)
                .build();
        assertThat(result.getEffects()).containsExactly(effects1, effects2);
    }

    public void getByEffect() throws Exception {
        CommonsStatisticsBuilder builder = new CommonsStatisticsBuilder();
        Statistics result = builder.with()
                .effectsOrder(effectList)
                .outcome(effects2, "1", 1)
                .outcome(effects2, "2", 2)
                .outcome(effects2, "3", 3)
                .outcome(effects1, "1", 4)
                .outcome(effects1, "2", 5)
                .outcome(effects1, "3", 6)
                .build();
        ObjectMatrix effectResults = result.getRowsAsOutcomes();
        assertThat(effectResults.getEntry(0,0)).isEqualTo(4);
        assertThat(effectResults.getEntry(0,1)).isEqualTo(1);
        assertThat(effectResults.getEntry(1,0)).isEqualTo(5);
        assertThat(effectResults.getEntry(1,1)).isEqualTo(2);
        assertThat(effectResults.getEntry(2,0)).isEqualTo(6);
        assertThat(effectResults.getEntry(2,1)).isEqualTo(3);
    }
}