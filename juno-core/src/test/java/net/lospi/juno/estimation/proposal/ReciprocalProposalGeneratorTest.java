/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import cern.jet.random.engine.RandomEngine;
import net.lospi.juno.elements.Chain;
import net.lospi.juno.model.Model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class ReciprocalProposalGeneratorTest {
    private SpecificProposalGenerator insertion;
    private SpecificProposalGenerator deletion;
    private RandomEngine engine;
    private Chain state;
    private Proposal expectedResult;
    private Model model;

    @BeforeMethod
    public void setUp(){
        insertion = mock(SpecificProposalGenerator.class);
        deletion = mock(SpecificProposalGenerator.class);
        engine = mock(RandomEngine.class);
        state = mock(Chain.class);
        expectedResult = mock(Proposal.class);
        model = mock(Model.class);
    }

    public void testGenerateInsertion() throws Exception {
        ReciprocalProposalGenerator generator = new ReciprocalProposalGenerator(insertion, deletion, engine);
        when(engine.nextDouble()).thenReturn(0.49D);
        when(insertion.generate(state, model)).thenReturn(expectedResult);
        Proposal result = generator.generate(state, model);
        assertThat(result).isEqualTo(expectedResult);
    }

    public void testGenerateDeletion() throws Exception {
        ReciprocalProposalGenerator generator = new ReciprocalProposalGenerator(insertion, deletion, engine);
        when(engine.nextDouble()).thenReturn(0.99D);
        when(deletion.generate(state, model)).thenReturn(expectedResult);
        Proposal result = generator.generate(state, model);
        assertThat(result).isEqualTo(expectedResult);
    }

    public void defaultInsertionProbability() throws Exception {
        new ReciprocalProposalGenerator(insertion, deletion, engine);
        verify(insertion).setInsertionProbability(0.5D);
        verify(deletion).setInsertionProbability(0.5D);
    }

    public void setInsertionProbability() throws Exception {
        ReciprocalProposalGenerator generator = new ReciprocalProposalGenerator(insertion, deletion, engine);
        verify(insertion).setInsertionProbability(0.5D);
        verify(deletion).setInsertionProbability(0.5D);
        generator.setInsertionProbability(0.3D);
        verify(insertion).setInsertionProbability(0.3D);
        verify(deletion).setInsertionProbability(0.7D);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Insertion probability must be on \\[0,1\\].")
    public void setInsertionProbabilityTooLow() throws Exception {
        ReciprocalProposalGenerator generator = new ReciprocalProposalGenerator(insertion, deletion, engine);
        generator.setInsertionProbability(-0.1D);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Insertion probability must be on \\[0,1\\].")
    public void setInsertionProbabilityTooHigh() throws Exception {
        ReciprocalProposalGenerator generator = new ReciprocalProposalGenerator(insertion, deletion, engine);
        generator.setInsertionProbability(1.1D);
    }
}
