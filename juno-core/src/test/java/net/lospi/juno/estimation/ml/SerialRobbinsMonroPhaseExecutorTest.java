package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.sim.ParametricProcess;
import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.ObjectVector;
import net.lospi.juno.model.SafeObjectMatrix;
import net.lospi.juno.model.SafeObjectVector;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class SerialRobbinsMonroPhaseExecutorTest {
    int minimumIterations, maximumIterations;
    private ObjectMatrix weight;
    private double gain;
    private SerialRobbinsMonroUtil serialRobbinsMonroUtil;
    private SafeObjectVector root;
    private ParametricProcess process;
    private StreamingRealVectorCalculator streamingRealVectorCalculator;
    private int parameterSize;
    private SafeObjectVector parameters1, parameters2, parameters3;
    private ObjectVector autocorrelation1, autocorrelation2, autocorrelation3, step1, step2, step3,
            average1, average2, average3;
    private List index;

    @BeforeMethod
    public void setUp() {
        parameterSize = 3;
        gain = 10D;
        average1 = mock(SafeObjectVector.class);
        average2 = mock(SafeObjectVector.class);
        average3 = mock(SafeObjectVector.class);
        parameters1 = mock(SafeObjectVector.class);
        parameters2 = mock(SafeObjectVector.class);
        parameters3 = mock(SafeObjectVector.class);
        autocorrelation1 = mock(SafeObjectVector.class);
        autocorrelation2 = mock(SafeObjectVector.class);
        autocorrelation3 = mock(SafeObjectVector.class);
        step1 = mock(SafeObjectVector.class);
        step2 = mock(SafeObjectVector.class);
        step3 = mock(SafeObjectVector.class);
        root = mock(SafeObjectVector.class);
        process = mock(ParametricProcess.class);
        streamingRealVectorCalculator = mock(StreamingRealVectorCalculator.class);
        serialRobbinsMonroUtil = mock(SerialRobbinsMonroUtil.class);
        weight = mock(SafeObjectMatrix.class);
        index = mock(List.class);
        minimumIterations = 100;
        maximumIterations = 300;

        when(process.getIndex()).thenReturn(index);
        when(process.parameterSize()).thenReturn(parameterSize);

        when(serialRobbinsMonroUtil.sampleNextStep(process, weight, root, gain))
                .thenReturn(step1)
                .thenReturn(step2)
                .thenReturn(step3)
                .thenThrow(new RuntimeException("Too many calls to sample"));

        when(process.addToAndSetParameters(step1)).thenReturn(parameters1);
        when(process.addToAndSetParameters(step2)).thenReturn(parameters2);
        when(process.addToAndSetParameters(step3)).thenReturn(parameters3);
        when(streamingRealVectorCalculator.calculateAutocorrelation(step1)).thenReturn(autocorrelation1);
        when(streamingRealVectorCalculator.calculateAutocorrelation(step2)).thenReturn(autocorrelation2);
        when(streamingRealVectorCalculator.calculateAutocorrelation(step3)).thenReturn(autocorrelation3);
        when(streamingRealVectorCalculator.calculateAverage(parameters1)).thenReturn(average1);
        when(streamingRealVectorCalculator.calculateAverage(parameters2)).thenReturn(average2);
        when(streamingRealVectorCalculator.calculateAverage(parameters3)).thenReturn(average3);
        when(serialRobbinsMonroUtil.needToContinue(1, minimumIterations, maximumIterations, parameters1, autocorrelation1)).thenReturn(true);
        when(serialRobbinsMonroUtil.needToContinue(2, minimumIterations, maximumIterations, parameters2, autocorrelation2)).thenReturn(true);
        when(serialRobbinsMonroUtil.needToContinue(3, minimumIterations, maximumIterations, parameters3, autocorrelation3)).thenReturn(false);
    }

    public void executePhase() {
        SerialRobbinsMonroPhaseExecutor underStudy = new SerialRobbinsMonroPhaseExecutor(serialRobbinsMonroUtil, streamingRealVectorCalculator);
        ObjectVector result = underStudy.executeSubPhase(process, weight, root, gain, minimumIterations, maximumIterations);
        assertThat(result).isEqualTo(average3);
        verify(streamingRealVectorCalculator).initialize(index);
    }

    public void canLogWithoutError() {
        SerialRobbinsMonroPhaseExecutor underStudy = new SerialRobbinsMonroPhaseExecutor(serialRobbinsMonroUtil, streamingRealVectorCalculator);
        when(serialRobbinsMonroUtil.sampleNextStep(process, weight, root, gain)).thenReturn(step1);
        when(serialRobbinsMonroUtil.needToContinue(anyInt(), anyInt(), anyInt(), any(SafeObjectVector.class), any(SafeObjectVector.class)))
                .thenAnswer(new Answer<Boolean>() {
                    private int calls;

                    @Override
                    public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                        return calls++ <= SerialRobbinsMonroPhaseExecutor.LOGGER_UPDATE_INTERVAL;
                    }
                });
        underStudy.executeSubPhase(process, weight, root, gain, minimumIterations, maximumIterations);
    }
}