package net.lospi.juno.estimation.sim;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.estimation.calc.LikelihoodDerivativesCalculator;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.model.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class SerialScoreSimulatorTest {
    private SafeObjectVector step;
    private LikelihoodDerivativesSimulator likelihoodDerivativesSimulator;
    private int parameterSize;
    private ObjectVector sample, negativeSample, newParameter;
    private int samplingInterval;
    private Model model, expectedModel;
    private LikelihoodDerivativesCalculator likelihoodDerivativesCalculator;
    private CommonsModelBuilder commonsModelBuilder;
    private CommonsModelBuilder.Stub stub;
    private List<Effect> effects;
    private List<LikelihoodDerivatives> derivatives;

    @BeforeMethod
    public void setUp() {
        effects = mock(List.class);
        expectedModel = mock(Model.class);
        stub = mock(CommonsModelBuilder.Stub.class);
        commonsModelBuilder = mock(CommonsModelBuilder.class);
        negativeSample = mock(SafeObjectVector.class);
        parameterSize = 20;
        samplingInterval = 30;
        step = mock(SafeObjectVector.class);
        model = mock(Model.class);
        ObjectVector parameter = mock(SafeObjectVector.class);
        likelihoodDerivativesSimulator = mock(LikelihoodDerivativesSimulator.class);
        likelihoodDerivativesCalculator = mock(LikelihoodDerivativesCalculator.class);
        sample = mock(SafeObjectVector.class);
        newParameter = mock(SafeObjectVector.class);
        LikelihoodDerivatives derivative = mock(LikelihoodDerivatives.class);
        derivatives = ImmutableList.of(derivative);

        when(model.getAllEffectsSize()).thenReturn(parameterSize);
        when(likelihoodDerivativesSimulator.simulateLikelihoodDerivatives(model, 1)).thenReturn(derivatives);
        when(likelihoodDerivativesCalculator.calculate(derivatives, effects)).thenReturn(derivative);
        when(derivative.getScore()).thenReturn(sample);
        when(sample.mapMultiply(-1D)).thenReturn(negativeSample);
        when(model.getAllEffectsParameter()).thenReturn(parameter);
        when(parameter.add(step)).thenReturn(newParameter);

        when(commonsModelBuilder.newModel()).thenReturn(stub);
        when(stub.from(model)).thenReturn(stub);
        when(stub.withNewParameter(newParameter)).thenReturn(stub);
        when(stub.build()).thenReturn(expectedModel).thenReturn(expectedModel);
        when(model.getAllEffects()).thenReturn(effects);
    }

    public void addToAndSetParameters() {
        SerialScoreSimulator underStudy = new SerialScoreSimulator(likelihoodDerivativesSimulator, likelihoodDerivativesCalculator, commonsModelBuilder);
        underStudy.setModel(model);
        ObjectVector result = underStudy.addToAndSetParameters(step);
        assertThat(result).isEqualTo(newParameter);
        assertThat(underStudy.getModel()).isEqualTo(expectedModel);
    }

    public void sample() {
        SerialScoreSimulator underStudy = new SerialScoreSimulator(likelihoodDerivativesSimulator, likelihoodDerivativesCalculator, commonsModelBuilder);
        underStudy.setModel(model);
        underStudy.setSamplingInterval(samplingInterval);
        ObjectVector result = underStudy.sample();
        assertThat(result).isEqualTo(negativeSample);
        verify(likelihoodDerivativesSimulator).setSamplingInterval(samplingInterval);
    }

    public void sampleWithDrawsPerSet() {
        int drawsPerSample = 100;
        when(likelihoodDerivativesSimulator.simulateLikelihoodDerivatives(model, drawsPerSample)).thenReturn(derivatives);
        SerialScoreSimulator underStudy = new SerialScoreSimulator(likelihoodDerivativesSimulator, likelihoodDerivativesCalculator, commonsModelBuilder);
        underStudy.setDrawsPerSample(drawsPerSample);
        underStudy.setModel(model);
        underStudy.setSamplingInterval(samplingInterval);
        ObjectVector result = underStudy.sample();
        assertThat(result).isEqualTo(negativeSample);
        verify(likelihoodDerivativesSimulator).simulateLikelihoodDerivatives(model, drawsPerSample);
        verify(likelihoodDerivativesSimulator).setSamplingInterval(samplingInterval);
    }

    public void parameterSize() {
        SerialScoreSimulator underStudy = new SerialScoreSimulator(likelihoodDerivativesSimulator, likelihoodDerivativesCalculator, commonsModelBuilder);
        underStudy.setModel(model);
        int result = underStudy.parameterSize();
        assertThat(result).isEqualTo(parameterSize);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void parameterSizeNoModel() {
        SerialScoreSimulator underStudy = new SerialScoreSimulator(likelihoodDerivativesSimulator, likelihoodDerivativesCalculator, commonsModelBuilder);
        underStudy.parameterSize();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void addToAndSetParametersNoModel() {
        SerialScoreSimulator underStudy = new SerialScoreSimulator(likelihoodDerivativesSimulator, likelihoodDerivativesCalculator, commonsModelBuilder);
        underStudy.addToAndSetParameters(step);
    }
}