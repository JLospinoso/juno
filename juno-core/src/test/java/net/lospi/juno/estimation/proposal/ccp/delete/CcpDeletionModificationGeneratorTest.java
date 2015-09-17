/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.MinistepChainCalculator;
import net.lospi.juno.estimation.proposal.ChainSegment;
import net.lospi.juno.estimation.proposal.ChainSegmentReplacementAlterSelectionLogRatioCalculator;
import net.lospi.juno.estimation.proposal.Modification;
import net.lospi.juno.estimation.proposal.ChainSegmentAlterSelectionProbabilitiesCalculator;
import net.lospi.juno.model.Model;
import net.lospi.juno.stat.UniformDistribution;
import org.javatuples.Pair;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class CcpDeletionModificationGeneratorTest {
    private UniformDistribution uniformDistribution;
    private CcpDeletionModificationBuilder builder;
    private ChainSegmentReplacementAlterSelectionLogRatioCalculator calculator;
    private int min, max;
    private Chain state;
    private Modification expected;
    private List<Pair<Integer, Integer>> pairList;
    private Pair<Integer, Integer> selectedPair;
    private Integer firstIndex, secondIndex;
    private Double logProbability;
    private CcpDeletionModificationBuilder.Stub stub;
    private CcpDeletionChainSegmentReplacementGenerator ccpDeletionChainSegmentReplacementGenerator;
    private ChainSegment replacement;
    private ChainSegmentAlterSelectionProbabilitiesCalculator chainSegmentAlterSelectionProbabilitiesCalculator;
    private Model model;
    private MinistepChainCalculator ministepChainCalculator;

    @BeforeMethod
    public void setUp() throws Exception {
        ministepChainCalculator = mock(MinistepChainCalculator.class);
        chainSegmentAlterSelectionProbabilitiesCalculator = mock(ChainSegmentAlterSelectionProbabilitiesCalculator.class);
        min = 5;
        max = 25;
        state = mock(Chain.class);
        expected = mock(Modification.class);
        uniformDistribution = mock(UniformDistribution.class);
        builder = mock(CcpDeletionModificationBuilder.class);
        calculator = mock(ChainSegmentReplacementAlterSelectionLogRatioCalculator.class);
        pairList = mock(List.class);
        stub = mock(CcpDeletionModificationBuilder.Stub.class);
        ccpDeletionChainSegmentReplacementGenerator = mock(CcpDeletionChainSegmentReplacementGenerator.class);
        replacement = mock(ChainSegment.class);
        firstIndex = 10;
        secondIndex = 20;
        logProbability = -1.234;
        selectedPair = new Pair<Integer, Integer>(firstIndex, secondIndex);
        model = mock(Model.class);

        when(ccpDeletionChainSegmentReplacementGenerator.generate(state, firstIndex, secondIndex)).thenReturn(replacement);
        when(ministepChainCalculator.getConsecutivelyCancelingPairs(state, min, max)).thenReturn(pairList);
        when(uniformDistribution.next(pairList)).thenReturn(selectedPair);
        when(calculator.calculate(firstIndex, secondIndex, replacement, state)).thenReturn(logProbability);
        when(builder.with()).thenReturn(stub);
        when(stub.start(firstIndex)).thenReturn(stub);
        when(stub.end(secondIndex)).thenReturn(stub);
        when(stub.logAlterSelectionProbability(logProbability)).thenReturn(stub);
        when(stub.replacement(replacement)).thenReturn(stub);
        when(stub.identity()).thenReturn(stub);
        when(stub.build()).thenReturn(expected);
    }

    public void testGenerate() throws Exception {
        CcpDeletionModificationGenerator generator = new CcpDeletionModificationGenerator(uniformDistribution,
                builder, calculator, min, max, ccpDeletionChainSegmentReplacementGenerator, chainSegmentAlterSelectionProbabilitiesCalculator, ministepChainCalculator);
        when(pairList.size()).thenReturn(20);
        when(pairList.isEmpty()).thenReturn(false);
        Modification result = generator.generate(state, model);
        assertThat(result).isEqualTo(expected);
        verify(stub).logAlterSelectionProbability(logProbability);
        verify(stub).replacement(replacement);
        verify(stub).start(firstIndex);
        verify(stub).end(secondIndex);
        verify(stub).build();
        verifyNoMoreInteractions(stub);
    }

    public void identityIfEmpty() throws Exception {
        CcpDeletionModificationGenerator generator = new CcpDeletionModificationGenerator(uniformDistribution,
                builder, calculator, min, max, ccpDeletionChainSegmentReplacementGenerator, chainSegmentAlterSelectionProbabilitiesCalculator, ministepChainCalculator);
        when(pairList.isEmpty()).thenReturn(true);
        Modification result = generator.generate(state, model);
        assertThat(result).isEqualTo(expected);
        verify(stub).identity();
        verify(stub).build();
        verifyNoMoreInteractions(stub);
    }
}
