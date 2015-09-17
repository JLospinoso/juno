/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.permute;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.ChainSegment;
import net.lospi.juno.estimation.proposal.ChainSegmentDeepCopier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class PermutationChainSegmentReplacementGeneratorTest {
    private Chain state;
    private ChainSegmentDeepCopier chainSegmentDeepCopier;
    private int endingIndex;
    private int startingIndex;
    private ChainSegment expected, shallowCopy;

    @BeforeMethod
    public void setUp() {
        startingIndex = 4;
        endingIndex = 8;
        chainSegmentDeepCopier = mock(ChainSegmentDeepCopier.class);
        state = mock(Chain.class);
        expected = mock(ChainSegment.class);
        shallowCopy = mock(ChainSegment.class);
        when(chainSegmentDeepCopier.copy(shallowCopy)).thenReturn(expected);
        when(state.segment(startingIndex, endingIndex)).thenReturn(shallowCopy);
    }

    public void generate() {
        PermutationChainSegmentReplacementGenerator underStudy = new PermutationChainSegmentReplacementGenerator(chainSegmentDeepCopier);
        ChainSegment result = underStudy.generate(state, startingIndex, endingIndex);
        assertThat(result).isEqualTo(expected);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Your indices must preserve ordering \\(end >= start\\)\\.")
    public void throwsWhenEndIndexTooCloseToStart() {
        PermutationChainSegmentReplacementGenerator underStudy = new PermutationChainSegmentReplacementGenerator(chainSegmentDeepCopier);
        underStudy.generate(state, startingIndex, startingIndex-1);
    }
}
