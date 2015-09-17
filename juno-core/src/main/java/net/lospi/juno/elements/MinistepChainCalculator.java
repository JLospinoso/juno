package net.lospi.juno.elements;

import org.javatuples.Pair;

import java.util.List;

/**
 * Created by jalospinoso on 6/14/2015.
 */
public interface MinistepChainCalculator {
    int getDiagonalLinksCount(Chain chain);
    int getConsecutivelyCancellingPairsCount(Chain chain, int minimumCcpLength, int maximumCcpLength);
    Integer indexOfNextMatchingLinkAfter(Chain chain, int start, Link link);
    Integer indexOfSecondMatchingLinkAfter(Chain chain, int start, Link link);
    List<String> getAltersFor(Chain chain, ActorAspect selectedActorAspect);
    int getAlterCountFor(Chain chain, ActorAspect selectedActorAspect);
    List<Integer> getDiagonalLinkIndices(Chain chain);
    List<Pair<Integer, Integer>> getConsecutivelyCancelingPairs(Chain chain,
                                                                int minSegmentLength, int maxSegmentLength);
}
