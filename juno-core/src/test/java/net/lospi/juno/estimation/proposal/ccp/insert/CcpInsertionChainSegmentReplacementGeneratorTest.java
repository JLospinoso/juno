/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.insert;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.Link;
import net.lospi.juno.estimation.proposal.ChainSegment;
import net.lospi.juno.estimation.proposal.ChainSegmentDeepCopier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class CcpInsertionChainSegmentReplacementGeneratorTest {
    private Chain state;
    private ChainSegmentDeepCopier chainSegmentDeepCopier;
    private int endingIndex;
    private Link linkToInsert;
    private int startingIndex;
    private ChainSegment expected, shallowCopy;
    private Link insertionLinkCopy1, insertionLinkCopy2;

    @BeforeMethod
    public void setUp() {
        startingIndex = 4;
        endingIndex = 8;
        chainSegmentDeepCopier = mock(ChainSegmentDeepCopier.class);
        linkToInsert = mock(Link.class);
        state = mock(Chain.class);
        expected = mock(ChainSegment.class);
        shallowCopy = mock(ChainSegment.class);
        insertionLinkCopy1 = mock(Link.class);
        insertionLinkCopy2 = mock(Link.class);
        when(chainSegmentDeepCopier.copy(shallowCopy)).thenReturn(expected);
        when(state.segment(startingIndex, endingIndex - 2)).thenReturn(shallowCopy);
        when(linkToInsert.deepCopy()).thenReturn(insertionLinkCopy1).thenReturn(insertionLinkCopy2);
    }

    public void generate() {
        CcpInsertionChainSegmentReplacementGenerator underStudy = new CcpInsertionChainSegmentReplacementGenerator(chainSegmentDeepCopier);
        ChainSegment result = underStudy.generate(state, startingIndex, endingIndex, linkToInsert);
        assertThat(result).isEqualTo(expected);

        verify(expected).prepend(insertionLinkCopy1);
        verify(expected).append(insertionLinkCopy2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Your indices must preserve ordering \\(end \\+ 2 >= start\\)\\.")
    public void throwsWhenEndIndexTooCloseToStart() {
        CcpInsertionChainSegmentReplacementGenerator underStudy = new CcpInsertionChainSegmentReplacementGenerator(chainSegmentDeepCopier);
        underStudy.generate(state, startingIndex, startingIndex+1, linkToInsert);
    }
}
