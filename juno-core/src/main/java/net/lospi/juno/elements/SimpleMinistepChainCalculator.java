package net.lospi.juno.elements;

import org.javatuples.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SimpleMinistepChainCalculator implements MinistepChainCalculator {

    @Override
    public int getDiagonalLinksCount(Chain chain) {
        return getDiagonalLinkIndices(chain).size();
    }

    @Override
    public int getConsecutivelyCancellingPairsCount(Chain chain, int minimumCcpLength, int maximumCcpLength) {
        return getConsecutivelyCancelingPairs(chain, minimumCcpLength, maximumCcpLength).size();
    }

    @Override
    public Integer indexOfNextMatchingLinkAfter(Chain chain, int start, Link link) {
        for(int index = start + 1; index < chain.getSize(); index++) {
            if(link.equals(chain.getAt(index))) {
                return index;
            }
        }
        return null;
    }

    @Override
    public Integer indexOfSecondMatchingLinkAfter(Chain chain, int start, Link link) {
        Integer firstMatch = indexOfNextMatchingLinkAfter(chain, start, link);
        if(firstMatch == null) {
            return null;
        }
        return indexOfNextMatchingLinkAfter(chain, firstMatch, link);
    }

    @Override
    public List<String> getAltersFor(Chain chain, ActorAspect selectedActorAspect) {
        String actor = selectedActorAspect.getActor();
        String aspect = selectedActorAspect.getAspect();
        List<String> result = new LinkedList<String>();
        for(ActorAspect actorAspect : chain.getActorAspects()){
            if(actorAspect.getAspect().equals(aspect) && !actorAspect.getActor().equals(actor)){
                result.add(actorAspect.getActor());
            }
        }
        return result;
    }

    @Override
    public int getAlterCountFor(Chain chain, ActorAspect selectedActorAspect) {
        String aspect = selectedActorAspect.getAspect();
        int result = 0;
        for(ActorAspect actorAspect : chain.getActorAspects()){
            if(!actorAspect.equals(selectedActorAspect) && aspect.equals(actorAspect.getAspect())){
                result++;
            }
        }
        return result;
    }

    @Override
    public List<Integer> getDiagonalLinkIndices(Chain chain) {
        List<Integer> result = new LinkedList<Integer>();
        for(int index=0; index < chain.getSize(); index++) {
            Link link = chain.getAt(index);
            if(link.getClass().equals(Ministep.class)){
                Ministep ministep = (Ministep)link;
                if(ministep.getActorAspect().getActor().equals(ministep.getAlter())){
                    result.add(index);
                }
            }
        }
        return result;
    }

    @Override
    public List<Pair<Integer, Integer>> getConsecutivelyCancelingPairs(Chain chain,
                    int minSegmentLength, int maxSegmentLength) {
        Map<Ministep, List<Integer>> nonDiagonalLookup = new HashMap<Ministep, List<Integer>>();
        int localIndex = 0;
        for(int index=0; index < chain.getSize(); index++) {
            Link link = chain.getAt(index);
            if(link.getClass().equals(Ministep.class)){
                findAllEquivalentMinisteps(nonDiagonalLookup, localIndex, (Ministep) link);
            }
            localIndex++;
        }
        List<Pair<Integer, Integer>> result = new LinkedList<Pair<Integer, Integer>>();
        for(List<Integer> indices : nonDiagonalLookup.values()){
            for(int i=1; i < indices.size(); i++){
                addCcpToList(minSegmentLength, maxSegmentLength, result, indices, i);
            }
        }
        return result;
    }

    private void addCcpToList(int minSegmentLength, int maxSegmentLength,
                              List<Pair<Integer, Integer>> result, List<Integer> indices, int i) {
        int first = indices.get(i-1);
        int last = indices.get(i);
        int length = last - first - 1;
        if(length >= minSegmentLength && length <= maxSegmentLength) {
            result.add(Pair.with(first, last));
        }
    }

    private void findAllEquivalentMinisteps(Map<Ministep, List<Integer>> nonDiagonalLookup,
                               int linkIndex, Ministep ministep) {
        String ego = ministep.getActorAspect().getActor();
        String alter = ministep.getAlter();
        if(!ego.equals(alter)){
            List<Integer> existingIndices = nonDiagonalLookup.get(ministep);
            if(existingIndices == null) {
                existingIndices = new LinkedList<Integer>();
                nonDiagonalLookup.put(ministep, existingIndices);
            }
            existingIndices.add(linkIndex);
        }
    }
}
