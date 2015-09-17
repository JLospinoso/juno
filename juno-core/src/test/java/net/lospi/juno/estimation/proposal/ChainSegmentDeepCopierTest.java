/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.elements.ChainSegmentBuilder;
import net.lospi.juno.elements.Link;
import net.lospi.juno.estimation.proposal.ChainSegment;
import net.lospi.juno.estimation.proposal.ChainSegmentDeepCopier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class ChainSegmentDeepCopierTest {
    private ChainSegmentBuilder chainSegmentBuilder;
    private ChainSegment original;
    private Link s1o, s2o, s1c, s2c;
    private ChainSegmentBuilder.Stub stub;
    private ChainSegment expected;

    @BeforeMethod
    public void setUp() throws Exception {
        s1o = mock(Link.class);
        s2o = mock(Link.class);
        s1c = mock(Link.class);
        s2c = mock(Link.class);
        original = mock(ChainSegment.class);
        expected = mock(ChainSegment.class);
        stub = mock(ChainSegmentBuilder.Stub.class);
        chainSegmentBuilder = mock(ChainSegmentBuilder.class);

        when(original.links()).thenReturn(ImmutableList.of(s1o, s2o));
        when(chainSegmentBuilder.with()).thenReturn(stub);
        when(s1o.deepCopy()).thenReturn(s1c);
        when(s2o.deepCopy()).thenReturn(s2c);
        when(stub.ministeps(ImmutableList.of(s1c, s2c))).thenReturn(stub);
        when(stub.build()).thenReturn(expected);
    }

    public void testCopy() throws Exception {
        ChainSegmentDeepCopier copier = new ChainSegmentDeepCopier(chainSegmentBuilder);
        ChainSegment result = copier.copy(original);
        assertThat(result).isEqualTo(expected);
    }
}