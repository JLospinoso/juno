/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.elements;

import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class ChainSegmentBuilderTest {
    public void with() throws Exception {
        List<Link> links = mock(List.class);
        ChainSegmentBuilder builder = new ChainSegmentBuilder();
        builder.with().ministeps(links).build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "You must set the links\\.")
    public void noLinks() throws Exception {
        ChainSegmentBuilder builder = new ChainSegmentBuilder();
        builder.with().build();
    }
}