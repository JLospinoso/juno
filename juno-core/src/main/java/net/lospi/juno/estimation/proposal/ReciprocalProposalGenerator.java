/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import cern.jet.random.engine.RandomEngine;
import net.lospi.juno.elements.Chain;
import net.lospi.juno.model.Model;

public class ReciprocalProposalGenerator implements ProposalGenerator {
    private SpecificProposalGenerator insertionProposalGenerator;
    private SpecificProposalGenerator deletionProposalGenerator;
    private RandomEngine engine;
    private double insertionProbability = 0.5D;

    public ReciprocalProposalGenerator(SpecificProposalGenerator insertionProposalGenerator,
                                       SpecificProposalGenerator deletionProposalGenerator,
                                       RandomEngine engine) {
        this.insertionProposalGenerator = insertionProposalGenerator;
        this.deletionProposalGenerator = deletionProposalGenerator;
        this.engine = engine;
        setFieldInsertionProbabilities();
    }

    @Override
    public Proposal generate(Chain state, Model model) {
        if(engine.nextDouble() <= insertionProbability){
            return insertionProposalGenerator.generate(state, model);
        } else {
            return deletionProposalGenerator.generate(state, model);
        }
    }

    public void setInsertionProbability(double insertionProbability){
        if(insertionProbability > 1D || insertionProbability < 0D){
            throw new IllegalArgumentException("Insertion probability must be on [0,1].");
        }
        this.insertionProbability = insertionProbability;
        setFieldInsertionProbabilities();
    }

    private void setFieldInsertionProbabilities(){
        insertionProposalGenerator.setInsertionProbability(insertionProbability);
        deletionProposalGenerator.setInsertionProbability(1D - insertionProbability);
    }
}
