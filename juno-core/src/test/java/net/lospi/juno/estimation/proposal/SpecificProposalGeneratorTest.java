/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.diagonal.insert.DiagonalNetworkMinistepInsertionModificationGenerator;
import net.lospi.juno.model.Model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class SpecificProposalGeneratorTest {
    private AlterSelectionLikelihoodRatioCalculator alterSelectionLikelihoodRatioCalculator;
    private EgoLikelihoodRatioCalculator egoLikelihoodRatioCalculator;
    private KappaFactorCalculator kappaFactorCalculator;
    private ProposalRatioCalculator proposalRatioCalculator;
    private ProposalBuilder proposalBuilder;
    private ModificationGenerator modificationGenerator;
    private Chain state;
    private Modification modification;
    private Model model;
    private double kappaFactor = -0.2;
    private double insertionProbability = 0.6D;
    private double proposalRatio = 10D;
    private double acceptanceProbability;
    private Proposal proposal;
    private ProposalBuilder.Stub stub;
    double alterRatio = -5;
    double egoRatio = 0;

    @BeforeMethod
    public void setUp(){
        acceptanceProbability = alterRatio + kappaFactor + egoRatio + proposalRatio;
        state = mock(Chain.class);
        modification = mock(Modification.class);
        model = mock(Model.class);
        alterSelectionLikelihoodRatioCalculator = mock(AlterSelectionLikelihoodRatioCalculator.class);
        modificationGenerator = mock(DiagonalNetworkMinistepInsertionModificationGenerator.class);
        alterSelectionLikelihoodRatioCalculator = mock(AlterSelectionLikelihoodRatioCalculator.class);
        kappaFactorCalculator = mock(KappaFactorCalculator.class);
        egoLikelihoodRatioCalculator = mock(EgoLikelihoodRatioCalculator.class);
        proposalRatioCalculator = mock(ProposalRatioCalculator.class);
        proposalBuilder = mock(ProposalBuilder.class);
        stub = mock(ProposalBuilder.Stub.class);
        proposal = mock(Proposal.class);


        when(proposalBuilder.with()).thenReturn(stub);
        when(modificationGenerator.generate(state, model)).thenReturn(modification);
        when(alterSelectionLikelihoodRatioCalculator.calculate(modification, state, model)).thenReturn(alterRatio);
        when(kappaFactorCalculator.calculate(model, state, modification)).thenReturn(kappaFactor);
        when(egoLikelihoodRatioCalculator.calculate(modification, state, model)).thenReturn(egoRatio);
        when(proposalRatioCalculator.calculateLogProposalRatio(insertionProbability, modification, state)).thenReturn(proposalRatio);
        when(stub.modification(modification)).thenReturn(stub);
        when(stub.logAcceptanceProbability(acceptanceProbability)).thenReturn(stub);
        when(stub.logAcceptanceProbability(Double.NEGATIVE_INFINITY)).thenReturn(stub);
        when(stub.build()).thenReturn(proposal);
    }

    public void testGenerate() throws Exception {
        when(modification.isIdentity()).thenReturn(false);
        SpecificProposalGenerator generator =
                new SpecificProposalGenerator(modificationGenerator, proposalBuilder,
                        proposalRatioCalculator, kappaFactorCalculator, egoLikelihoodRatioCalculator,
                        alterSelectionLikelihoodRatioCalculator);
        generator.setInsertionProbability(insertionProbability);
        Proposal result = generator.generate(state, model);
        assertThat(result).isEqualTo(proposal);

        verify(stub).logAcceptanceProbability(acceptanceProbability);
        verify(stub).modification(modification);
    }

    public void testGenerateIdentity() throws Exception {
        when(modification.isIdentity()).thenReturn(true);
        SpecificProposalGenerator generator =
                new SpecificProposalGenerator(modificationGenerator, proposalBuilder,
                        proposalRatioCalculator, kappaFactorCalculator, egoLikelihoodRatioCalculator,
                        alterSelectionLikelihoodRatioCalculator);
        generator.setInsertionProbability(insertionProbability);
        Proposal result = generator.generate(state, model);
        assertThat(result).isEqualTo(proposal);

        verify(stub).logAcceptanceProbability(Double.NEGATIVE_INFINITY);
        verify(stub).modification(modification);
    }
}
