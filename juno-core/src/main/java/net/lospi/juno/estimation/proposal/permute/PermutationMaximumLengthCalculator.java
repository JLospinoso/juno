/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.permute;

import net.lospi.juno.elements.Chain;

public class PermutationMaximumLengthCalculator {
    public int calculate(int maxLength, int start, Chain state) {
        int chainLength = state.getSize();
        int remainingChainLength = chainLength - start;
        return Math.min(maxLength, remainingChainLength);
    }
}
