/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.insert;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.Link;
import net.lospi.juno.elements.MinistepChainCalculator;

public class CcpInsertionMaximumLengthCalculator {
    private final MinistepChainCalculator ministepChainCalculator;

    public CcpInsertionMaximumLengthCalculator(MinistepChainCalculator ministepChainCalculator) {
        this.ministepChainCalculator = ministepChainCalculator;
    }

    public int calculate(int maximumCcpLength, int start, Chain chain) {
        Link linkAtDelete = chain.getAt(start);
        Integer nextIndexOfLink = ministepChainCalculator.indexOfNextMatchingLinkAfter(chain, start, linkAtDelete);
        int impliedCcpMaximum = start + maximumCcpLength;
        int limit;
        if(nextIndexOfLink == null) {
            int chainLength = chain.getSize();
            if(chainLength < impliedCcpMaximum) {
                limit = chainLength;
            } else {
                limit = impliedCcpMaximum;
            }
        } else {
            if(nextIndexOfLink < impliedCcpMaximum) {
                limit = nextIndexOfLink;
            } else {
                limit = impliedCcpMaximum;
            }
        }
        return limit - start;
    }
}
