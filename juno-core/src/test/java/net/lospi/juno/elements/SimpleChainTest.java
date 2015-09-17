/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.elements;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.proposal.ChainSegment;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class SimpleChainTest {
    private List<ActorAspect> actorAspects;
    private State state;
    private Link link1, link2;
    private ChainSegmentBuilder chainSegmentBuilder;
    private LikelihoodDerivatives derivatives1, derivatives2;

    @BeforeMethod
    public void setUp(){
        link1 = mock(Link.class);
        link2 = mock(Link.class);
        derivatives1 = mock(LikelihoodDerivatives.class);
        derivatives2 = mock(LikelihoodDerivatives.class);
        actorAspects = mock(List.class);
        state = mock(State.class);
        chainSegmentBuilder = mock(ChainSegmentBuilder.class);
    }

    public void rewind() {
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.append(link1);
        chain.append(link2);
        chain.fastForward();
        verify(link1).forwardApply(state);
        verify(link2).forwardApply(state);
        chain.rewind();
        verify(link1).backwardApply(state);
        verify(link2).backwardApply(state);
    }

    public void fastForward() {
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.append(link1);
        chain.append(link2);
        chain.fastForward();
        verify(link1).forwardApply(state);
        verify(link2).forwardApply(state);
    }

    public void stateAtForward() {
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.append(link1);
        chain.append(link2);
        chain.stateAt(1);
        verify(link1).forwardApply(state);
        verify(link2).forwardApply(state);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void stateAtForwardTooFar() {
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.append(link1);
        chain.append(link2);
        chain.stateAt(3);
    }

    public void stateBackward() {
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.append(link1);
        chain.append(link2);
        chain.fastForward();

        chain.stateAt(0);
        verify(link2).backwardApply(state);

        chain.stateAt(-1);
        verify(link1).backwardApply(state);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void stateBackwardTooFar() {
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.append(link1);
        chain.append(link2);
        chain.fastForward();
        chain.stateAt(-2);
    }

    public void segment() throws Exception {
        Link link1 = mock(Link.class);
        Link link2 = mock(Link.class);
        Link link3 = mock(Link.class);
        Link link4 = mock(Link.class);
        Link linkCopy2 = mock(Link.class);
        Link linkCopy3 = mock(Link.class);
        derivatives1 = mock(LikelihoodDerivatives.class);
        derivatives2 = mock(LikelihoodDerivatives.class);

        when(link2.deepCopy()).thenReturn(linkCopy2);
        when(link3.deepCopy()).thenReturn(linkCopy3);

        chainSegmentBuilder = new ChainSegmentBuilder();
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.append(link1);
        chain.append(link2);
        chain.append(link3);
        chain.append(link4);

        ChainSegment result = chain.segment(1, 2);
        assertThat(result.links().get(0)).isEqualTo(linkCopy2);
        assertThat(result.links().get(1)).isEqualTo(linkCopy3);
    }


    public void segmentWithAlterSelectionProbabilties() throws Exception {
        Link link1 = mock(Link.class);
        Link link2 = mock(Link.class);
        Link link3 = mock(Link.class);
        Link link4 = mock(Link.class);
        Link linkCopy2 = mock(Link.class);
        Link linkCopy3 = mock(Link.class);
        when(link2.deepCopy()).thenReturn(linkCopy2);
        when(link3.deepCopy()).thenReturn(linkCopy3);
        when(link2.getLikelihoodDerivatives()).thenReturn(derivatives1);
        when(link3.getLikelihoodDerivatives()).thenReturn(derivatives2);

        chainSegmentBuilder = new ChainSegmentBuilder();
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.append(link1);
        chain.append(link2);
        chain.append(link3);
        chain.append(link4);

        ChainSegment result = chain.segmentWithLikelihoodDerivatives(1, 2);

        verify(linkCopy2).setLikelihoodDerivatives(derivatives1);
        verify(linkCopy3).setLikelihoodDerivatives(derivatives2);
        assertThat(result.links().get(0)).isEqualTo(linkCopy2);
        assertThat(result.links().get(1)).isEqualTo(linkCopy3);
    }


    public void testContainsPinnedLinks() throws Exception {
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        assertThat(chain.containsPinnedLinks()).isFalse();
    }

    public void replaceFrom(){
        Link link1 = mock(Link.class);
        Link link2 = mock(Link.class);
        Link link3 = mock(Link.class);
        Link link4 = mock(Link.class);
        Link link5 = mock(Link.class);
        Link linkToDelete = mock(Link.class);

        ChainSegment replacement = new ChainSegment(ImmutableList.of(link3, link4));
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.insertAt(0, link1);
        chain.insertAt(1, link2);
        chain.insertAt(2, linkToDelete);
        chain.insertAt(3, linkToDelete);
        chain.insertAt(4, linkToDelete);
        chain.insertAt(5, link5);
        chain.replaceFrom(2, 4, replacement);
        assertThat(chain.getAt(0)).isEqualTo(link1);
        assertThat(chain.getAt(1)).isEqualTo(link2);
        assertThat(chain.getAt(2)).isEqualTo(link3);
        assertThat(chain.getAt(3)).isEqualTo(link4);
        assertThat(chain.getAt(4)).isEqualTo(link5);
        assertThat(chain.getSize()).isEqualTo(5);
    }

    public void testGetSize() throws Exception {
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        assertThat(chain.getSize()).isZero();
        chain.insertAt(0, link1);
        assertThat(chain.getSize()).isEqualTo(1);
        chain.insertAt(0, link1);
        assertThat(chain.getSize()).isEqualTo(2);
        chain.deleteAt(1);
        assertThat(chain.getSize()).isEqualTo(1);
        chain.deleteAt(0);
        assertThat(chain.getSize()).isZero();
    }

    public void testGetAt() throws Exception {
        Link other = mock(Link.class);
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.append(other);
        chain.append(other);
        chain.append(other);
        chain.append(other);
        chain.append(link1); //4
        chain.append(other);
        assertThat(chain.getAt(4)).isEqualTo(link1);
    }

    public void testGetActorAspects() throws Exception {
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        assertThat(chain.getActorAspects()).isEqualTo(actorAspects);
    }

    public void testGetActorAspectsSize() throws Exception {
        when(actorAspects.size()).thenReturn(6);
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        assertThat(chain.getActorAspectCount()).isEqualTo(6);
    }

    public void getState() throws Exception {
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        assertThat(chain.stateAt(-1)).isEqualTo(state);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Cannot insert below index 0")
    public void insertBelowZero() throws Exception {
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.insertAt(0, link1);
        chain.insertAt(-1, link1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Cannot insert above chain length index")
    public void insertAboveLength() throws Exception {
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.append(link1);
        chain.append(link1);
        chain.append(link1);
        chain.insertAt(4, link1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Cannot delete below index 0")
    public void deleteBelowZero() throws Exception {
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.insertAt(0, link1);
        chain.deleteAt(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Cannot delete at or above chain length index")
    public void deleteAboveLength() throws Exception {
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.append(link1);
        chain.append(link1);
        chain.append(link1);
        chain.deleteAt(3);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void getDerivativesWhenNotCalculated() throws Exception {
        when(link1.getLikelihoodDerivatives()).thenReturn(null);
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.append(link1);
        List<LikelihoodDerivatives> results = chain.getLinkLikelihoodDerivatives();
        assertThat(results).containsExactly(derivatives1, derivatives2);
    }

    public void getDerivatives() throws Exception {
        when(link1.getLikelihoodDerivatives()).thenReturn(derivatives1);
        when(link2.getLikelihoodDerivatives()).thenReturn(derivatives2);
        SimpleChain chain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        chain.append(link1);
        chain.append(link2);
        List<LikelihoodDerivatives> results = chain.getLinkLikelihoodDerivatives();
        assertThat(results).containsExactly(derivatives1, derivatives2);
    }
}
