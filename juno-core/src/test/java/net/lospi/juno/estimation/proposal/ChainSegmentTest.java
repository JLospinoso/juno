/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */
package net.lospi.juno.estimation.proposal;

import net.lospi.juno.elements.Link;
import java.util.List;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups="unit")
public class ChainSegmentTest {
    private Link prependDeepCopy;
    private List links;
    private Link appendDeepCopy;

    @BeforeMethod
    public void setUp() {
        links = mock(List.class);
        appendDeepCopy = mock(Link.class);
        prependDeepCopy = mock(Link.class);
    }

    public void links() {
        ChainSegment underStudy = new ChainSegment(links);
        List<Link> result = underStudy.links();
        assertThat(result).isEqualTo(links);
    }

    public void append() {
        ChainSegment underStudy = new ChainSegment(links);
        underStudy.append(appendDeepCopy);
        assertThat(links).isEqualTo(links);
        verify(links).add(appendDeepCopy);
    }

    public void prepend() {
        ChainSegment underStudy = new ChainSegment(links);
        underStudy.prepend(prependDeepCopy);
        assertThat(links).isEqualTo(links);
        verify(links).add(0, prependDeepCopy);
    }
}