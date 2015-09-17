/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */
package net.lospi.juno.elements;

import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class MinistepTest {
    private String ego, alter, aspect;
    private ActorAspect egoAspect;
    private LikelihoodDerivatives derivatives;
    private State state;
    private Network network;

    @BeforeMethod
    public void setUp() {
        derivatives = mock(LikelihoodDerivatives.class);
        alter = "alter";
        aspect = "aspect";
        ego = "ego";
        egoAspect = mock(ActorAspect.class);
        state = mock(State.class);
        network = mock(Network.class);
        when(egoAspect.getAspect()).thenReturn(aspect);
        when(egoAspect.getActor()).thenReturn(ego);
    }

    public void getAlter() {
        Ministep underStudy = new Ministep(egoAspect, alter);
        String result = underStudy.getAlter();
        assertThat(result).isEqualTo(alter);
    }

    public void getActorAspect() {
        Ministep underStudy = new Ministep(egoAspect, alter);
        ActorAspect result = underStudy.getActorAspect();
        assertThat(result).isEqualTo(egoAspect);
    }

    public void hashCodeWorks() {
        Ministep underStudy = new Ministep(egoAspect, alter);
        Ministep other = new Ministep(egoAspect, "otherAlter");
        int result = underStudy.hashCode();
        assertThat(result).isNotEqualTo(other.hashCode());
    }

    public void nullNotEqual() {
        Ministep underStudy = new Ministep(egoAspect, alter);
        boolean result = underStudy.equals(null);
        assertThat(result).isFalse();
    }

    public void notEqualDifferentClass() {
        Ministep underStudy = new Ministep(egoAspect, alter);
        boolean result = underStudy.equals("foo");
        assertThat(result).isFalse();
    }

    public void same() {
        Ministep underStudy = new Ministep(egoAspect, alter);
        boolean result = underStudy.equals(underStudy);
        assertThat(result).isTrue();
    }

    public void equals() {
        Ministep underStudy = new Ministep(egoAspect, alter);
        Ministep other = new Ministep(egoAspect, alter);
        boolean result = underStudy.equals(other);
        assertThat(result).isTrue();
    }

    public void notEqualsDifferentAlter() {
        Ministep underStudy = new Ministep(egoAspect, alter);
        Ministep other = new Ministep(egoAspect, "other");
        boolean result = underStudy.equals(other);
        assertThat(result).isFalse();
    }

    public void notEqualsDifferentEgoAspect() {
        Ministep underStudy = new Ministep(egoAspect, alter);
        Ministep other = new Ministep(mock(ActorAspect.class), alter);
        boolean result = underStudy.equals(other);
        assertThat(result).isFalse();
    }

    public void deepCopy() {
        Ministep underStudy = new Ministep(egoAspect, alter);
        Link result = underStudy.deepCopy();
        assertThat(result).isEqualTo(underStudy);
        assertThat(result).isNotSameAs(underStudy);
    }

    public void setLogAlterSelectionProbability() {
        Ministep underStudy = new Ministep(egoAspect, alter);
        underStudy.setLikelihoodDerivatives(derivatives);
        LikelihoodDerivatives result = underStudy.getLikelihoodDerivatives();
        assertThat(result).isEqualTo(derivatives);
    }

    public void canToString() {
        Ministep underStudy = new Ministep(egoAspect, alter);
        assertThat(underStudy.toString()).isNotNull();
    }

    public void forwardApply() {
        Ministep underStudy = new Ministep(egoAspect, alter);
        when(state.getNetwork(aspect)).thenReturn(network);
        underStudy.backwardApply(state);
        verify(network).flipTie(ego, alter);
        verifyNoMoreInteractions(network);
    }

    public void backwardApply() {
        Ministep underStudy = new Ministep(egoAspect, alter);
        when(state.getNetwork(aspect)).thenReturn(network);
        underStudy.forwardApply(state);
        verify(network).flipTie(ego, alter);
        verifyNoMoreInteractions(network);
    }
}
