/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.Link;
import net.lospi.juno.elements.MinistepChainCalculator;
import net.lospi.juno.estimation.proposal.Modification;
import net.lospi.juno.estimation.proposal.ModificationGenerator;
import net.lospi.juno.model.Model;
import net.lospi.juno.stat.UniformDistribution;

import java.util.List;

public class DiagonalNetworkMinistepDeletionModificationGenerator implements ModificationGenerator {
    private UniformDistribution uniform;
    private DiagonalNetworkMinistepDeletionModificationBuilder builder;
    private final MinistepChainCalculator ministepChainCalculator;

    public DiagonalNetworkMinistepDeletionModificationGenerator(UniformDistribution uniform,
             DiagonalNetworkMinistepDeletionModificationBuilder builder, MinistepChainCalculator ministepChainCalculator) {
        this.uniform = uniform;
        this.builder = builder;
        this.ministepChainCalculator = ministepChainCalculator;
    }

    @Override
    public Modification generate(Chain state, Model model) {
        List<Integer> diagonalIndices = ministepChainCalculator.getDiagonalLinkIndices(state);
        if(diagonalIndices.isEmpty()){
            return builder.with().identity().build();
        }
        int deletionIndex = uniform.next(diagonalIndices);
        Link linkToDelete = state.getAt(deletionIndex);
        double logAlterSelectionProbability = linkToDelete.getLikelihoodDerivatives().getLogLikelihood();
        return builder.with()
                .index(deletionIndex)
                .logAlterSelectionProbability(logAlterSelectionProbability)
                .build();
    }
}
