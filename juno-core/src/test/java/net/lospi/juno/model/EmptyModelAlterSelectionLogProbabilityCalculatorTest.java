/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.model;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.elements.Network;
import net.lospi.juno.elements.State;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.stat.CachedNaturalLogarithm;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class EmptyModelAlterSelectionLogProbabilityCalculatorTest {
    private ActorAspect actorAspect;
    private String actor, networkName;
    private State state;
    private double expected;
    private CachedNaturalLogarithm naturalLogarithm;
    private Network network;
    private Model model;

    @BeforeMethod
    public void setUp() throws Exception {
        actor = "actor";
        networkName = "net";

        expected = -1.234D;
        actorAspect = mock(ActorAspect.class);
        state = mock(State.class);
        naturalLogarithm = mock(CachedNaturalLogarithm.class);
        network = mock(Network.class);
        model = mock(Model.class);

        when(actorAspect.getAspect()).thenReturn(networkName);
        when(state.getNetwork(networkName)).thenReturn(network);
        when(network.getActorCount()).thenReturn(31);
        when(naturalLogarithm.apply(30)).thenReturn(-1 * expected);
    }

    public void testCalculate() throws Exception {
        EmptyModelAlterSelectionLogProbabilityCalculator calculator = new EmptyModelAlterSelectionLogProbabilityCalculator(naturalLogarithm);
        LikelihoodDerivatives result = calculator.calculate(actorAspect, actor, model, state);
        assertThat(result.getLogLikelihood()).isEqualTo(expected);
    }
}
