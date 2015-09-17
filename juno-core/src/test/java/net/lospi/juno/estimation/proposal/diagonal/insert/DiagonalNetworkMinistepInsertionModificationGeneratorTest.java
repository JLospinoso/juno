/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.insert;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.State;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.proposal.Modification;
import net.lospi.juno.model.AlterSelectionLogProbabilityCalculator;
import net.lospi.juno.model.Model;
import net.lospi.juno.stat.UniformDistribution;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class DiagonalNetworkMinistepInsertionModificationGeneratorTest {
    private UniformDistribution uniform;
    private DiagonalNetworkMinistepInsertionModificationBuilder builder;
    private Chain chain;
    private State state;
    private ActorAspect chosenActorAspect;
    private List<ActorAspect> actorAspects;
    private int stateLength = 10;
    private int chosenIndex = 4;
    private Modification modification;
    private DiagonalNetworkMinistepInsertionModificationBuilder.Stub ongoingBuilder;
    private AlterSelectionLogProbabilityCalculator alterSelectionLogProbabilityCalculator;
    private String actor = "actor";
    private Model model;
    private LikelihoodDerivatives derivatives;

    @BeforeMethod
    public void setUp(){
        uniform = mock(UniformDistribution.class);
        builder = mock(DiagonalNetworkMinistepInsertionModificationBuilder.class);
        chain = mock(Chain.class);
        chosenActorAspect = mock(ActorAspect.class);
        actorAspects = mock(List.class);
        modification = mock(Modification.class);
        ongoingBuilder = mock(DiagonalNetworkMinistepInsertionModificationBuilder.Stub.class);
        alterSelectionLogProbabilityCalculator = mock(AlterSelectionLogProbabilityCalculator.class);
        state = mock(State.class);
        model = mock(Model.class);
        derivatives = mock(LikelihoodDerivatives.class);

        when(chain.getSize()).thenReturn(stateLength);
        when(chain.getActorAspects()).thenReturn(actorAspects);
        when(uniform.next(actorAspects)).thenReturn(chosenActorAspect);
        when(builder.with()).thenReturn(ongoingBuilder);
        when(ongoingBuilder.actorAspect(chosenActorAspect)).thenReturn(ongoingBuilder);
        when(ongoingBuilder.index(chosenIndex)).thenReturn(ongoingBuilder);
        when(ongoingBuilder.linkLikelihoodDerivatives(derivatives)).thenReturn(ongoingBuilder);
        when(ongoingBuilder.build()).thenReturn(modification);
        when(chosenActorAspect.getActor()).thenReturn(actor);
        when(chain.stateAt(chosenIndex-1)).thenReturn(state); //TODO: Do we want this mismatching?
        when(alterSelectionLogProbabilityCalculator.calculate(chosenActorAspect, actor, model, state)).thenReturn(derivatives);
    }

    public void withPinning() throws Exception {
        DiagonalNetworkMinistepInsertionModificationGenerator generator = new DiagonalNetworkMinistepInsertionModificationGenerator(uniform, builder, alterSelectionLogProbabilityCalculator);
        when(chain.containsPinnedLinks()).thenReturn(true);
        when(uniform.next(1, 9)).thenReturn(chosenIndex);
        Modification result = generator.generate(chain, model);

        assertThat(result).isEqualTo(modification);

        verify(ongoingBuilder).actorAspect(chosenActorAspect);
        verify(ongoingBuilder).linkLikelihoodDerivatives(derivatives);
        verify(ongoingBuilder).index(chosenIndex);
    }

    public void withoutPinning() throws Exception {
        DiagonalNetworkMinistepInsertionModificationGenerator generator = new DiagonalNetworkMinistepInsertionModificationGenerator(uniform, builder, alterSelectionLogProbabilityCalculator);
        when(chain.containsPinnedLinks()).thenReturn(false);
        when(uniform.next(0, 10)).thenReturn(chosenIndex);
        Modification result = generator.generate(chain, model);

        assertThat(result).isEqualTo(modification);

        verify(ongoingBuilder).actorAspect(chosenActorAspect);
        verify(ongoingBuilder).linkLikelihoodDerivatives(derivatives);
        verify(ongoingBuilder).index(chosenIndex);
    }
}
