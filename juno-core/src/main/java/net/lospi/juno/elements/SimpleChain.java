/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.elements;

import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.proposal.ChainSegment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SimpleChain implements Chain {
    private final List<ActorAspect> actorAspects;
    private final State state;
    private final List<Link> links;
    private final ChainSegmentBuilder chainSegmentBuilder;
    private int indexOfState = -1;

    public SimpleChain(List<ActorAspect> actorAspects, State state, ChainSegmentBuilder chainSegmentBuilder) {
        this.actorAspects = actorAspects;
        this.chainSegmentBuilder = chainSegmentBuilder;
        this.state = state;
        links = new LinkedList<Link>();
    }

    @Override
    public boolean containsPinnedLinks() {
        return false;
    }

    @Override
    public int getSize() {
        return links.size();
    }

    @Override
    public List<ActorAspect> getActorAspects() {
        return actorAspects;
    }

    @Override
    public void deleteAt(int deletionIndex) {
        if(deletionIndex < 0){
            throw new IllegalArgumentException("Cannot delete below index 0");
        }
        if(deletionIndex >= getSize()){
            throw new IllegalArgumentException("Cannot delete at or above chain length index");
        }
        rewind();
        links.remove(deletionIndex);
    }

    @Override
    public int getActorAspectCount() {
        return actorAspects.size();
    }

    @Override
    public State stateAt(int index) {
        navigateTo(index);
        return state;
    }

    @Override
    public Link getAt(int index) {
        return links.get(index);
    }

    @Override
    public ChainSegment segment(int start, int end) {
        List<Link> result = new ArrayList<Link>();
        for(int i=start; i<=end; i++){
            Link toAdd = links.get(i);
            Link copy = toAdd.deepCopy();
            result.add(copy);
        }
        return chainSegmentBuilder.with()
                .ministeps(result)
                .build();
    }

    @Override
    public ChainSegment segmentWithLikelihoodDerivatives(int start, int end) {
        List<Link> result = new ArrayList<Link>();
        for(int i=start; i<=end; i++){
            Link toAdd = links.get(i);
            Link copy = toAdd.deepCopy();
            copy.setLikelihoodDerivatives(toAdd.getLikelihoodDerivatives());
            result.add(copy);
        }
        return chainSegmentBuilder.with()
                .ministeps(result)
                .build();
    }

    @Override
    public void replaceFrom(int start, int end, ChainSegment replacement) {
        int elementsToRemove = end-start+1;
        while(elementsToRemove > 0){
            deleteAt(start);
            elementsToRemove--;
        }
        int index = start;
        for(Link link : replacement.links()){
            insertAt(index, link);
            index++;
        }
    }

    @Override
    public void insertAt(int insertionIndex, Link other) {
        if(insertionIndex < 0){
            throw new IllegalArgumentException("Cannot insert below index 0");
        }
        if(insertionIndex > getSize()){
            throw new IllegalArgumentException("Cannot insert above chain length index");
        }
        rewind();
        links.add(insertionIndex, other);
    }

    @Override
    public List<LikelihoodDerivatives> getLinkLikelihoodDerivatives() {
        List<LikelihoodDerivatives> result = new ArrayList<LikelihoodDerivatives>(links.size());
        for(Link link : links) {
            LikelihoodDerivatives derivative = link.getLikelihoodDerivatives();
            if(derivative == null) {
                throw new IllegalStateException("You must calculateAutocorrelation link likelihoods.");
            }
            result.add(derivative);
        }
        return result;
    }

    @Override
    public void append(Link element) {
        links.add(element);
    }

    private boolean advance() {
        if (indexOfState +1 < links.size()) {
            indexOfState++;
            links.get(indexOfState).forwardApply(state);
            return true;
        }
        return false;
    }

    private boolean retreat() {
        if (indexOfState >= 0) {
            links.get(indexOfState).backwardApply(state);
            indexOfState--;
            return true;
        }
        return false;
    }

    private void navigateTo(int desiredIndex) {
        if (desiredIndex < -1 || desiredIndex >= links.size()) {
            throw new IllegalArgumentException("Desired index must be within links range.");
        }
        while (indexOfState < desiredIndex) {
            advance();
        }
        while (indexOfState > desiredIndex) {
            retreat();
        }
    }


    protected void fastForward() {
        while (advance()) {
            //
        }
    }

    protected void rewind() {
        while (retreat()) {
            //
        }
    }
}
