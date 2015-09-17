/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.Link;
import net.lospi.juno.elements.MinistepChainCalculator;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.proposal.Modification;
import net.lospi.juno.model.Model;
import net.lospi.juno.stat.UniformDistribution;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class DiagonalNetworkMinistepDeletionModificationGeneratorTest {
    private UniformDistribution uniform;
    private DiagonalNetworkMinistepDeletionModificationBuilder builder;
    private Chain state;
    private int chosenIndex = 4;
    private Modification modification;
    private List<Integer> diagonalLinkIndices;
    private DiagonalNetworkMinistepDeletionModificationBuilder.Stub stub;
    private LikelihoodDerivatives derivatives;
    private Model model;
    private MinistepChainCalculator ministepChainCalculator;

    @BeforeMethod
    public void setUp(){
        ministepChainCalculator = mock(MinistepChainCalculator.class);
        model = mock(Model.class);
        derivatives = mock(LikelihoodDerivatives.class);
        uniform = mock(UniformDistribution.class);
        builder = mock(DiagonalNetworkMinistepDeletionModificationBuilder.class);
        stub = mock(DiagonalNetworkMinistepDeletionModificationBuilder.Stub.class);
        state = mock(Chain.class);
        modification = mock(Modification.class);
        diagonalLinkIndices = (List<Integer>) mock(List.class);
        Link chosenLink = mock(Link.class);

        double logAlterSelectionProbability = -1.23456;
        when(derivatives.getLogLikelihood()).thenReturn(logAlterSelectionProbability);
        when(ministepChainCalculator.getDiagonalLinkIndices(state)).thenReturn(diagonalLinkIndices);
        when(state.getAt(chosenIndex)).thenReturn(chosenLink);
        when(chosenLink.getLikelihoodDerivatives()).thenReturn(derivatives);
        when(builder.with()).thenReturn(stub);
        when(stub.identity()).thenReturn(stub);
        when(stub.index(chosenIndex)).thenReturn(stub);
        when(stub.logAlterSelectionProbability(logAlterSelectionProbability)).thenReturn(stub);
        when(stub.build()).thenReturn(modification);
    }

    public void withNoDiagonals() throws Exception {
        when(diagonalLinkIndices.isEmpty()).thenReturn(true);
        DiagonalNetworkMinistepDeletionModificationGenerator generator = new DiagonalNetworkMinistepDeletionModificationGenerator(uniform, builder, ministepChainCalculator);
        Modification result = generator.generate(state, model);
        assertThat(result).isEqualTo(modification);
        verify(stub).identity();
    }

    public void selectsADiagonal() throws Exception {
        when(diagonalLinkIndices.isEmpty()).thenReturn(false);
        when(uniform.next(diagonalLinkIndices)).thenReturn(chosenIndex);
        when(ministepChainCalculator.getDiagonalLinkIndices(state)).thenReturn(diagonalLinkIndices);
        DiagonalNetworkMinistepDeletionModificationGenerator generator = new DiagonalNetworkMinistepDeletionModificationGenerator(uniform, builder, ministepChainCalculator);
        Modification result = generator.generate(state, model);
        assertThat(result).isEqualTo(modification);
        verify(stub).index(chosenIndex);
    }
}
