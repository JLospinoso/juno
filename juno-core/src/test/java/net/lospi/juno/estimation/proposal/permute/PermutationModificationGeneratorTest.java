package net.lospi.juno.estimation.proposal.permute;


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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class PermutationModificationGeneratorTest {
    private UniformDistribution uniformDistribution;
    private PermutationModificationBuilder permutationModificationBuilder;
    private ChainSegmentReplacementAlterSelectionLogRatioCalculator alterSelectionLogRatioCalculator;
    private PermutationChainSegmentReplacementGenerator permutationChainSegmentReplacementGenerator;
    private PermutationMaximumLengthCalculator permutationMaximumLengthCalculator;
    private int minLength;
    private int maxLength;
    private Chain state;
    private int ministepCount = 30;
    private int startingIndex = 4;
    private Link linkAtStartingIndex;
    private List<ActorAspect> actorAspects;
    private ActorAspect selectedActorAspect;
    private List<String> altersForSelectedActorAspect;
    private String selectedAlter;
    private int adjustedMaxLength;
    private int segmentLength;
    private int endingIndex;
    private ChainSegment replacement;
    private double logAlterSelectionProbability;
    private PermutationModificationBuilder.Stub permutationStub;
    private PermutationModification expected;
    private ChainSegmentAlterSelectionProbabilitiesCalculator chainSegmentAlterSelectionProbabilitiesCalculator;
    private Model model;
    private MinistepChainCalculator ministepChainCalculator;

    @BeforeMethod
    public void setUp(){
        ministepChainCalculator = mock(MinistepChainCalculator.class);
        chainSegmentAlterSelectionProbabilitiesCalculator = mock(ChainSegmentAlterSelectionProbabilitiesCalculator.class);
        uniformDistribution = mock(UniformDistribution.class);
        permutationModificationBuilder = mock(PermutationModificationBuilder.class);
        alterSelectionLogRatioCalculator = mock(ChainSegmentReplacementAlterSelectionLogRatioCalculator.class);
        permutationChainSegmentReplacementGenerator = mock(PermutationChainSegmentReplacementGenerator.class);
        permutationMaximumLengthCalculator = mock(PermutationMaximumLengthCalculator.class);
        minLength = 10;
        maxLength = 40;
        state = mock(Chain.class);
        linkAtStartingIndex = mock(Link.class);
        actorAspects = mock(List.class);
        selectedActorAspect = mock(ActorAspect.class);
        altersForSelectedActorAspect = mock(List.class);
        selectedAlter = "selectedAlter";
        model = mock(Model.class);
        adjustedMaxLength = 15;
        segmentLength = 4;
        endingIndex = startingIndex + segmentLength - 1;
        replacement = mock(ChainSegment.class);
        logAlterSelectionProbability = -1.2345d;
        permutationStub = mock(PermutationModificationBuilder.Stub.class);
        expected = mock(PermutationModification.class);

        when(state.getSize()).thenReturn(ministepCount);
        when(state.getAt(startingIndex)).thenReturn(linkAtStartingIndex);
        when(state.getActorAspects()).thenReturn(actorAspects);
        when(ministepChainCalculator.getAltersFor(state, selectedActorAspect)).thenReturn(altersForSelectedActorAspect);
        when(uniformDistribution.next(altersForSelectedActorAspect)).thenReturn(selectedAlter);
        when(permutationMaximumLengthCalculator.calculate(maxLength, startingIndex, state)).thenReturn(adjustedMaxLength);
        when(uniformDistribution.next(minLength, adjustedMaxLength)).thenReturn(segmentLength);
        when(permutationChainSegmentReplacementGenerator.generate(state, startingIndex, endingIndex)).thenReturn(replacement);
        when(alterSelectionLogRatioCalculator.calculate(startingIndex, endingIndex, replacement, state)).thenReturn(logAlterSelectionProbability);
        when(permutationModificationBuilder.with()).thenReturn(permutationStub);
        when(permutationStub.end(endingIndex)).thenReturn(permutationStub);
        when(permutationStub.identity()).thenReturn(permutationStub);
        when(permutationStub.start(startingIndex)).thenReturn(permutationStub);
        when(permutationStub.replacement(replacement)).thenReturn(permutationStub);
        when(permutationStub.logAlterSelectionProbability(logAlterSelectionProbability)).thenReturn(permutationStub);
        when(permutationStub.build()).thenReturn(expected);
    }

    public void generateWithChainToSmall() throws Exception {
        PermutationModificationGenerator generator = new PermutationModificationGenerator(uniformDistribution,
                permutationModificationBuilder, alterSelectionLogRatioCalculator, permutationChainSegmentReplacementGenerator,
                permutationMaximumLengthCalculator, minLength, maxLength, chainSegmentAlterSelectionProbabilitiesCalculator);
        when(state.getSize()).thenReturn(1);
        Modification result = generator.generate(state, model);
        assertThat(result).isEqualTo(expected);
        verify(permutationStub).identity();
    }

    public void generateWithPin() throws Exception {
        PermutationModificationGenerator generator = new PermutationModificationGenerator(uniformDistribution,
                permutationModificationBuilder, alterSelectionLogRatioCalculator, permutationChainSegmentReplacementGenerator,
                permutationMaximumLengthCalculator, minLength, maxLength, chainSegmentAlterSelectionProbabilitiesCalculator);
        when(state.containsPinnedLinks()).thenReturn(true);
        when(uniformDistribution.next(1, ministepCount - minLength)).thenReturn(startingIndex);
        Modification result = generator.generate(state, model);
        assertThat(result).isEqualTo(expected);
    }

    public void generateWithoutPin() throws Exception {
        PermutationModificationGenerator generator = new PermutationModificationGenerator(uniformDistribution,
                permutationModificationBuilder, alterSelectionLogRatioCalculator, permutationChainSegmentReplacementGenerator,
                permutationMaximumLengthCalculator, minLength, maxLength, chainSegmentAlterSelectionProbabilitiesCalculator);
        when(state.containsPinnedLinks()).thenReturn(false);
        when(uniformDistribution.next(0, ministepCount - minLength)).thenReturn(startingIndex);
        Modification result = generator.generate(state, model);
        assertThat(result).isEqualTo(expected);
    }
}