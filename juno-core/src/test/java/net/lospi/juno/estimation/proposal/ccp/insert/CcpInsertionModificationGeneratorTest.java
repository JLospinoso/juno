/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.insert;

import net.lospi.juno.elements.*;
import net.lospi.juno.estimation.proposal.ChainSegment;
import net.lospi.juno.estimation.proposal.ChainSegmentAlterSelectionProbabilitiesCalculator;
import net.lospi.juno.estimation.proposal.ChainSegmentReplacementAlterSelectionLogRatioCalculator;
import net.lospi.juno.estimation.proposal.Modification;
import net.lospi.juno.model.Model;
import net.lospi.juno.stat.UniformDistribution;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class CcpInsertionModificationGeneratorTest {
    private UniformDistribution uniformDistribution;
    private CcpInsertionModificationBuilder ccpInsertionModificationBuilder;
    private ChainSegmentReplacementAlterSelectionLogRatioCalculator alterSelectionLogRatioCalculator;
    private CcpInsertionChainSegmentReplacementGenerator ccpInsertionChainSegmentReplacementGenerator;
    private CcpInsertionMaximumLengthCalculator ccpInsertionMaximumLengthCalculator;
    private MinistepBuilder ministepBuilder;
    private MinistepBuilder.Stub ministepStub;
    private int minLength;
    private int maxLength;
    private Chain state;
    private int ministepCount = 30;
    private int startingIndex = 4;
    private Link linkAtStartingIndex;
    private ActorAspect actorAspectAtStartingIndex;
    private List<ActorAspect> actorAspects;
    private ActorAspect selectedActorAspect;
    private List<String> altersForSelectedActorAspect;
    private String selectedAlter;
    private Ministep linkToInsert;
    private int adjustedMaxLength;
    private int segmentLength;
    private int endingIndex;
    private ChainSegment replacement;
    private double logAlterSelectionProbability;
    private CcpInsertionModificationBuilder.Stub ccpInsertionStub;
    private CcpInsertionModification expected;
    private ChainSegmentAlterSelectionProbabilitiesCalculator chainSegmentAlterSelectionProbabilitiesCalculator;
    private MinistepChainCalculator ministepChainCalculator;
    private Model model;

    @BeforeMethod
    public void setUp(){
        model = mock(Model.class);
        ministepChainCalculator = mock(MinistepChainCalculator.class);
        chainSegmentAlterSelectionProbabilitiesCalculator = mock(ChainSegmentAlterSelectionProbabilitiesCalculator.class);
        uniformDistribution = mock(UniformDistribution.class);
        ccpInsertionModificationBuilder = mock(CcpInsertionModificationBuilder.class);
        alterSelectionLogRatioCalculator = mock(ChainSegmentReplacementAlterSelectionLogRatioCalculator.class);
        ccpInsertionChainSegmentReplacementGenerator = mock(CcpInsertionChainSegmentReplacementGenerator.class);
        ccpInsertionMaximumLengthCalculator = mock(CcpInsertionMaximumLengthCalculator.class);
        ministepBuilder = mock(MinistepBuilder.class);
        minLength = 10;
        maxLength = 40;
        state = mock(Chain.class);
        linkAtStartingIndex = mock(Link.class);
        actorAspectAtStartingIndex = mock(ActorAspect.class);
        actorAspects = mock(List.class);
        selectedActorAspect = mock(ActorAspect.class);
        altersForSelectedActorAspect = mock(List.class);
        selectedAlter = "selectedAlter";
        ministepStub = mock(MinistepBuilder.Stub.class);
        linkToInsert = mock(Ministep.class);
        adjustedMaxLength = 15;
        segmentLength = 4;
        endingIndex = startingIndex + segmentLength + 1;
        replacement = mock(ChainSegment.class);
        logAlterSelectionProbability = -1.2345d;
        ccpInsertionStub = mock(CcpInsertionModificationBuilder.Stub.class);
        expected = mock(CcpInsertionModification.class);

        when(state.getSize()).thenReturn(ministepCount);
        when(state.getAt(startingIndex)).thenReturn(linkAtStartingIndex);
        when(linkAtStartingIndex.getActorAspect()).thenReturn(actorAspectAtStartingIndex);
        when(state.getActorAspects()).thenReturn(actorAspects);
        when(uniformDistribution.nextNotEqualTo(actorAspectAtStartingIndex, actorAspects)).thenReturn(selectedActorAspect);
        when(ministepChainCalculator.getAltersFor(state, selectedActorAspect)).thenReturn(altersForSelectedActorAspect);
        when(uniformDistribution.next(altersForSelectedActorAspect)).thenReturn(selectedAlter);
        when(ministepBuilder.with()).thenReturn(ministepStub);
        when(ministepStub.alter(selectedAlter)).thenReturn(ministepStub);
        when(ministepStub.egoAspect(selectedActorAspect)).thenReturn(ministepStub);
        when(ministepStub.build()).thenReturn(linkToInsert);
        when(ccpInsertionMaximumLengthCalculator.calculate(maxLength, startingIndex, state)).thenReturn(adjustedMaxLength);
        when(uniformDistribution.next(minLength, adjustedMaxLength)).thenReturn(segmentLength);
        when(ccpInsertionChainSegmentReplacementGenerator.generate(state, startingIndex, endingIndex, linkToInsert)).thenReturn(replacement);
        when(alterSelectionLogRatioCalculator.calculate(startingIndex, endingIndex-2, replacement, state)).thenReturn(logAlterSelectionProbability);
        when(ccpInsertionModificationBuilder.with()).thenReturn(ccpInsertionStub);
        when(ccpInsertionStub.end(endingIndex)).thenReturn(ccpInsertionStub);
        when(ccpInsertionStub.identity()).thenReturn(ccpInsertionStub);
        when(ccpInsertionStub.start(startingIndex)).thenReturn(ccpInsertionStub);
        when(ccpInsertionStub.replacement(replacement)).thenReturn(ccpInsertionStub);
        when(ccpInsertionStub.egoAspect(selectedActorAspect)).thenReturn(ccpInsertionStub);
        when(ccpInsertionStub.logAlterSelectionProbability(logAlterSelectionProbability)).thenReturn(ccpInsertionStub);
        when(ccpInsertionStub.build()).thenReturn(expected);
    }

    public void generateWithChainToSmall() throws Exception {
        CcpInsertionModificationGenerator generator = new CcpInsertionModificationGenerator(uniformDistribution,
                ccpInsertionModificationBuilder, alterSelectionLogRatioCalculator, ccpInsertionChainSegmentReplacementGenerator,
                ccpInsertionMaximumLengthCalculator, ministepBuilder, minLength, maxLength, chainSegmentAlterSelectionProbabilitiesCalculator, ministepChainCalculator);
        when(state.getSize()).thenReturn(1);
        Modification result = generator.generate(state, model);
        assertThat(result).isEqualTo(expected);
        verify(ccpInsertionStub).identity();
    }

    public void generateWithPin() throws Exception {
        CcpInsertionModificationGenerator generator = new CcpInsertionModificationGenerator(uniformDistribution,
                ccpInsertionModificationBuilder, alterSelectionLogRatioCalculator, ccpInsertionChainSegmentReplacementGenerator,
                ccpInsertionMaximumLengthCalculator, ministepBuilder, minLength, maxLength, chainSegmentAlterSelectionProbabilitiesCalculator, ministepChainCalculator);
        when(state.containsPinnedLinks()).thenReturn(true);
        when(uniformDistribution.next(1, ministepCount - minLength)).thenReturn(startingIndex);
        Modification result = generator.generate(state, model);
        assertThat(result).isEqualTo(expected);
    }

    public void generateWithoutPin() throws Exception {
        CcpInsertionModificationGenerator generator = new CcpInsertionModificationGenerator(uniformDistribution,
                ccpInsertionModificationBuilder, alterSelectionLogRatioCalculator, ccpInsertionChainSegmentReplacementGenerator,
                ccpInsertionMaximumLengthCalculator, ministepBuilder, minLength, maxLength, chainSegmentAlterSelectionProbabilitiesCalculator, ministepChainCalculator);
        when(state.containsPinnedLinks()).thenReturn(false);
        when(uniformDistribution.next(0, ministepCount - minLength)).thenReturn(startingIndex);
        Modification result = generator.generate(state, model);
        assertThat(result).isEqualTo(expected);
    }
}