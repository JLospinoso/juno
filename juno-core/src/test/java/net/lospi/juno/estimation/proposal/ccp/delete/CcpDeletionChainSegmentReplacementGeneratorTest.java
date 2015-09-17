/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.ChainSegment;
import net.lospi.juno.estimation.proposal.ChainSegmentDeepCopier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class CcpDeletionChainSegmentReplacementGeneratorTest {
    private ChainSegmentDeepCopier chainSegmentDeepCopier;
    private ChainSegment replacement, expected;
    private Chain state;
    private int start, end;

    @BeforeMethod
    public void setUp() throws Exception {
        chainSegmentDeepCopier = mock(ChainSegmentDeepCopier.class);
        replacement = mock(ChainSegment.class);
        expected = mock(ChainSegment.class);
        state = mock(Chain.class);
        start = 4;
        end = 8;

        when(state.segment(start + 1, end - 1)).thenReturn(replacement);
        when(chainSegmentDeepCopier.copy(replacement)).thenReturn(expected);
    }

    public void testGenerate() throws Exception {
        CcpDeletionChainSegmentReplacementGenerator generator = new CcpDeletionChainSegmentReplacementGenerator(chainSegmentDeepCopier);
        ChainSegment result = generator.generate(state, start, end);
        assertThat(result).isEqualTo(expected);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Your indices must preserve ordering \\(end > start\\).")
    public void throwsWhenIndicesSame() throws Exception {
        CcpDeletionChainSegmentReplacementGenerator generator = new CcpDeletionChainSegmentReplacementGenerator(chainSegmentDeepCopier);
        generator.generate(state, 2, 2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Your indices must preserve ordering \\(end > start\\).")
    public void throwsWhenIndicesNotOrdered() throws Exception {
        CcpDeletionChainSegmentReplacementGenerator generator = new CcpDeletionChainSegmentReplacementGenerator(chainSegmentDeepCopier);
        generator.generate(state, 3, 2);
    }
}