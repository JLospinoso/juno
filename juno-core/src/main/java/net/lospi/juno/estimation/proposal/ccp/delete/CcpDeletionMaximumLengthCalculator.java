/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.Link;
import net.lospi.juno.elements.MinistepChainCalculator;

public class CcpDeletionMaximumLengthCalculator {
    private final MinistepChainCalculator ministepChainCalculator;

    public CcpDeletionMaximumLengthCalculator(MinistepChainCalculator ministepChainCalculator) {
        this.ministepChainCalculator = ministepChainCalculator;
    }

    public int calculate(int maximumCcpLength, int deletionStart, Chain chain) {
        int chainLength = chain.getSize();
        int chainLengthAdjustedCcpLengthMaximum = Math.min(chainLength - deletionStart + 1, maximumCcpLength);
        Link linkAtDelete = chain.getAt(deletionStart);
        Integer nextIndexOfLink = ministepChainCalculator.indexOfSecondMatchingLinkAfter(chain, deletionStart, linkAtDelete);
        if(nextIndexOfLink == null) {
            return chainLengthAdjustedCcpLengthMaximum;
        }
        int nextLinkAdjustedCcpLengthMaximum = nextIndexOfLink - deletionStart - 1;
        return Math.min(nextLinkAdjustedCcpLengthMaximum, chainLengthAdjustedCcpLengthMaximum);
    }
}
