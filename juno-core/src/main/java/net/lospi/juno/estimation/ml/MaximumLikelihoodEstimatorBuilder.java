/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.calc.LikelihoodDerivativesCalculator;
import net.lospi.juno.estimation.sim.ChainSimulator;
import net.lospi.juno.estimation.proposal.ProposalDistribution;
import net.lospi.juno.model.Model;

public interface MaximumLikelihoodEstimatorBuilder {
    MaximumLikelihoodEstimatorBuilder with();

    MaximumLikelihoodEstimatorBuilder chainDerivativesCalculator(LikelihoodDerivativesCalculator likelihoodDerivativesCalculator);

    MaximumLikelihoodEstimatorBuilder model(Model model);

    MaximumLikelihoodEstimatorBuilder proposalDistribution(ProposalDistribution proposalDistribution);

    MaximumLikelihoodEstimatorBuilder chainSimulator(ChainSimulator chainSimulator);

    MaximumLikelihoodEstimatorBuilder robbinsMonro(RobbinsMonro robbinsMonro);

    MaximumLikelihoodEstimator build();
}
