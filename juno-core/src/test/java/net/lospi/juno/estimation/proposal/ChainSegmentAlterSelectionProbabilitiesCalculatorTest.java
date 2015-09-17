/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */
package net.lospi.juno.estimation.proposal;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.elements.*;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.model.AlterSelectionLogProbabilityCalculator;
import net.lospi.juno.model.Model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.*;

@Test(groups="unit")
public class ChainSegmentAlterSelectionProbabilitiesCalculatorTest {
    private Chain chain;
    private AlterSelectionLogProbabilityCalculator alterSelectionLogProbabilityCalculator;
    private List<Link> links;
    private Ministep link1, link2;
    private LikelihoodDerivatives derivatives1, derivatives2;
    private int start = 5;
    private Model model;
    private State state;

    @BeforeMethod
    public void setUp() {
        derivatives1 = mock(LikelihoodDerivatives.class);
        derivatives2 = mock(LikelihoodDerivatives.class);
        model = mock(Model.class);

        alterSelectionLogProbabilityCalculator = mock(AlterSelectionLogProbabilityCalculator.class);
        chain = mock(Chain.class);
        link1 = mock(Ministep.class);
        link2 = mock(Ministep.class);
        links = ImmutableList.of((Link) link1, link2);
        ActorAspect aspect1 = mock(ActorAspect.class);
        ActorAspect aspect2 = mock(ActorAspect.class);
        String alter1 = "alter1";
        String alter2 = "alter2";
        state = mock(State.class);

        when(link1.getActorAspect()).thenReturn(aspect1);
        when(link2.getActorAspect()).thenReturn(aspect2);
        when(link1.getAlter()).thenReturn(alter1);
        when(link2.getAlter()).thenReturn(alter2);
        when(chain.stateAt(start-1)).thenReturn(state);
        when(alterSelectionLogProbabilityCalculator.calculate(aspect1, alter1, model, state)).thenReturn(derivatives1);
        when(alterSelectionLogProbabilityCalculator.calculate(aspect2, alter2, model, state)).thenReturn(derivatives2);
    }

    public void interactsWithStateCorrectly() {
        ChainSegmentAlterSelectionProbabilitiesCalculator calculator = new ChainSegmentAlterSelectionProbabilitiesCalculator(alterSelectionLogProbabilityCalculator);
        calculator.calculate(links, chain, model, start);
        verify(link1).setLikelihoodDerivatives(derivatives1);
        verify(link1).forwardApply(state);
        verify(link2).setLikelihoodDerivatives(derivatives2);
        verify(link2).forwardApply(state);
        verify(link1).backwardApply(state);
        verify(link1).backwardApply(state);
    }
}