/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import net.lospi.juno.elements.Link;

import java.util.List;

public class ChainSegment {
    private final List<Link> links;

    public ChainSegment(List<Link> links) {
        this.links = links;
    }
    public List<Link> links() {
        return links;
    }
    public void prepend(Link prependDeepCopy) {
        links.add(0, prependDeepCopy);
    }
    public void append(Link appendDeepCopy) {
        links.add(appendDeepCopy);
    }
}
