/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.model;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.elements.State;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class NullModelAlterSelectionLogProbabilityCalculatorTest {
    private ActorAspect actorAspect;
    private State state;
    private Model model;

    @BeforeMethod
    public void setUp(){
        actorAspect = mock(ActorAspect.class);
        state = mock(State.class);
        model = mock(Model.class);
    }

    public void testCalculate() throws Exception {
        NullModelAlterSelectionLogProbabilityCalculator calculator = new NullModelAlterSelectionLogProbabilityCalculator();
        assertThat(calculator.calculate(actorAspect, "actor", model, state).getLogLikelihood()).isZero();
    }
}