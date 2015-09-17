/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.insert;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.State;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.proposal.Modification;
import net.lospi.juno.estimation.proposal.ModificationGenerator;
import net.lospi.juno.model.AlterSelectionLogProbabilityCalculator;
import net.lospi.juno.model.Model;
import net.lospi.juno.stat.UniformDistribution;

public class DiagonalNetworkMinistepInsertionModificationGenerator implements ModificationGenerator {
    private final UniformDistribution uniform;
    private final DiagonalNetworkMinistepInsertionModificationBuilder builder;
    private final AlterSelectionLogProbabilityCalculator alterSelectionLogProbabilityCalculator;

    public DiagonalNetworkMinistepInsertionModificationGenerator(UniformDistribution uniform,
                         DiagonalNetworkMinistepInsertionModificationBuilder builder,
                         AlterSelectionLogProbabilityCalculator alterSelectionLogProbabilityCalculator) {
        this.uniform = uniform;
        this.builder = builder;
        this.alterSelectionLogProbabilityCalculator = alterSelectionLogProbabilityCalculator;
    }

    @Override
    public Modification generate(Chain chain, Model model) {
        int pinAdjustment;
        if(chain.containsPinnedLinks()){
            pinAdjustment = 1;
        } else {
            pinAdjustment = 0;
        }
        int insertionIndex = uniform.next(pinAdjustment, chain.getSize() - pinAdjustment);
        ActorAspect selectedActorAspect = uniform.next(chain.getActorAspects());
        State state = chain.stateAt(insertionIndex - 1);
        LikelihoodDerivatives likelihoodDerivatives = alterSelectionLogProbabilityCalculator.calculate(selectedActorAspect,
                selectedActorAspect.getActor(), model, state);
        return builder.with()
                .actorAspect(selectedActorAspect)
                .index(insertionIndex)
                .linkLikelihoodDerivatives(likelihoodDerivatives)
                .build();
    }
}
