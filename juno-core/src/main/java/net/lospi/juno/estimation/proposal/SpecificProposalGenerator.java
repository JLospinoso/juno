/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.model.Model;

public class SpecificProposalGenerator implements ProposalGenerator {
    private double insertionProbability;
    private double alterSelectionLogLikelihoodRatio;
    private double logKappaFactor;
    private double egoSelectionLogLikelihoodRatio;
    private double logProposalRatio;
    private Chain state;
    private Modification modification;
    private Model model;
    private AlterSelectionLikelihoodRatioCalculator alterSelectionLikelihoodRatioCalculator;
    private EgoLikelihoodRatioCalculator egoSelectionLogLikelihoodRatioCalculator;
    private KappaFactorCalculator kappaFactorCalculator;
    private ProposalRatioCalculator proposalRatioCalculator;
    private ProposalBuilder proposalBuilder;
    private ModificationGenerator modificationGenerator;

    public SpecificProposalGenerator(ModificationGenerator modificationGenerator, ProposalBuilder proposalBuilder,
                                     ProposalRatioCalculator proposalRatioCalculator,
                                     KappaFactorCalculator kappaFactorCalculator,
                                     EgoLikelihoodRatioCalculator egoSelectionLogLikelihoodRatioCalculator,
                                     AlterSelectionLikelihoodRatioCalculator alterSelectionLikelihoodRatioCalculator) {
        this.modificationGenerator = modificationGenerator;
        this.proposalBuilder = proposalBuilder;
        this.proposalRatioCalculator = proposalRatioCalculator;
        this.kappaFactorCalculator = kappaFactorCalculator;
        this.egoSelectionLogLikelihoodRatioCalculator = egoSelectionLogLikelihoodRatioCalculator;
        this.alterSelectionLikelihoodRatioCalculator = alterSelectionLikelihoodRatioCalculator;
        insertionProbability = 0.5D;
    }

    public void setInsertionProbability(double selectionProbability) {
        this.insertionProbability = selectionProbability;
    }

    @Override
    public Proposal generate(Chain state, Model model) {
        this.state = state;
        this.model = model;
        generateModification();
        if(modification.isIdentity()){
            return proposalBuilder
                    .with()
                    .modification(modification)
                    .logAcceptanceProbability(Double.NEGATIVE_INFINITY)
                    .build();
        }
        calculateKappaFactor();
        calculateEgoSelectionLogLikelihood();
        calculateAlterSelectionLogLikelihood();
        calculateProposalRatio();
        double logAcceptanceProbability = logKappaFactor + egoSelectionLogLikelihoodRatio + alterSelectionLogLikelihoodRatio + logProposalRatio;
        return proposalBuilder
                .with()
                .modification(modification)
                .logAcceptanceProbability(logAcceptanceProbability)
                .build();
    }

    private void generateModification() {
        modification = modificationGenerator.generate(state, model);
    }

    private void calculateAlterSelectionLogLikelihood() {
        alterSelectionLogLikelihoodRatio = alterSelectionLikelihoodRatioCalculator.calculate(modification, state, model);
    }

    private void calculateKappaFactor() {
        logKappaFactor = kappaFactorCalculator.calculate(model, state, modification);
    }

    private void calculateEgoSelectionLogLikelihood() {
        egoSelectionLogLikelihoodRatio = egoSelectionLogLikelihoodRatioCalculator.calculate(modification, state, model);
    }

    private void calculateProposalRatio() {
        logProposalRatio = proposalRatioCalculator.calculateLogProposalRatio(insertionProbability, modification, state);
    }
}
