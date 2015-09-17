/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.elements;

import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.proposal.ChainSegment;

import java.util.List;

public interface Chain {
    boolean containsPinnedLinks();
    int getSize();
    List<ActorAspect> getActorAspects();
    void deleteAt(int deletionIndex);
    int getActorAspectCount();
    State stateAt(int index);
    Link getAt(int index);
    ChainSegment segment(int start, int end);
    ChainSegment segmentWithLikelihoodDerivatives(int start, int end);
    void replaceFrom(int start, int end, ChainSegment replacement);
    void insertAt(int insertionIndex, Link other);
    List<LikelihoodDerivatives> getLinkLikelihoodDerivatives();
    void append(Link element);
}
