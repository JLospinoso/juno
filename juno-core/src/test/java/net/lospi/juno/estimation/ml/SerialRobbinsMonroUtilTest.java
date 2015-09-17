package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.sim.ParametricProcess;
import net.lospi.juno.estimation.util.RealVectorUtil;
import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.ObjectVector;
import net.lospi.juno.model.SafeObjectMatrix;
import net.lospi.juno.model.SafeObjectVector;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class SerialRobbinsMonroUtilTest {
    private ObjectMatrix weight;
    private ObjectVector autocorrelation, root, parameter, diff, step;
    private RealVectorUtil realVectorUtil;
    private ParametricProcess simulator;
    private double gain;
    private ObjectVector sample;
    int minimumIterations, maximumIterations;

    @BeforeMethod
    public void setUp() {
        gain = 100;
        minimumIterations = 10;
        maximumIterations = 100;

        root = mock(SafeObjectVector.class);
        simulator = mock(ParametricProcess.class);
        parameter = mock(SafeObjectVector.class);
        step = mock(SafeObjectVector.class);
        autocorrelation = mock(SafeObjectVector.class);
        diff = mock(SafeObjectVector.class);
        realVectorUtil = mock(RealVectorUtil.class);
        weight = mock(SafeObjectMatrix.class);
        sample = mock(SafeObjectVector.class);

        when(realVectorUtil.anyWithMagnitudeGreaterThan(parameter, SerialRobbinsMonroUtil.MAXIMUM_PARAMETER_MAGNITUDE)).thenReturn(false);
        when(realVectorUtil.anyPositive(autocorrelation)).thenReturn(true);
    }

    public void parametersAreHealthy() {
        when(realVectorUtil.anyWithMagnitudeGreaterThan(parameter, SerialRobbinsMonroUtil.MAXIMUM_PARAMETER_MAGNITUDE)).thenReturn(false);

        SerialRobbinsMonroUtil underStudy = new SerialRobbinsMonroUtil(realVectorUtil);
        boolean result = underStudy.parametersAreHealthy(parameter);

        assertThat(result).isTrue();
    }

    public void parametersAreNotHealthy() {
        when(realVectorUtil.anyWithMagnitudeGreaterThan(parameter, SerialRobbinsMonroUtil.MAXIMUM_PARAMETER_MAGNITUDE)).thenReturn(true);

        SerialRobbinsMonroUtil underStudy = new SerialRobbinsMonroUtil(realVectorUtil);
        boolean result = underStudy.parametersAreHealthy(parameter);

        assertThat(result).isFalse();
    }

    public void sampleNextStep() {
        ObjectVector expected = mock(SafeObjectVector.class);
        when(simulator.sample()).thenReturn(sample);
        when(sample.subtract(root)).thenReturn(diff);
        when(weight.preMultiply(diff)).thenReturn(step);
        when(step.mapMultiply(-1D * gain)).thenReturn(expected);

        SerialRobbinsMonroUtil underStudy = new SerialRobbinsMonroUtil(realVectorUtil);
        ObjectVector result = underStudy.sampleNextStep(simulator, weight, root, gain);
        assertThat(result).isEqualTo(expected);
    }

    public void needToContinueAboveMaximumIsFalse() {
        SerialRobbinsMonroUtil underStudy = new SerialRobbinsMonroUtil(realVectorUtil);
        int iterations = maximumIterations + 1;
        boolean result = underStudy.needToContinue(iterations, minimumIterations, maximumIterations, parameter, autocorrelation);
        assertThat(result).isFalse();
    }

    public void needToContinueBelowMinimumTrue() {
        SerialRobbinsMonroUtil underStudy = new SerialRobbinsMonroUtil(realVectorUtil);
        int iterations = minimumIterations - 1;
        boolean result = underStudy.needToContinue(iterations, minimumIterations, maximumIterations, parameter, autocorrelation);
        assertThat(result).isTrue();
    }

    public void needToContinueUnhealthyParameter() {
        when(realVectorUtil.anyWithMagnitudeGreaterThan(parameter, SerialRobbinsMonroUtil.MAXIMUM_PARAMETER_MAGNITUDE)).thenReturn(true);
        int iterations = 50;
        SerialRobbinsMonroUtil underStudy = new SerialRobbinsMonroUtil(realVectorUtil);
        boolean result = underStudy.needToContinue(iterations, minimumIterations, maximumIterations, parameter, autocorrelation);
        assertThat(result).isFalse();
    }

    public void needToContinueNegativeAutocorrelation() {
        when(realVectorUtil.anyPositive(autocorrelation)).thenReturn(false);
        int iterations = 50;
        SerialRobbinsMonroUtil underStudy = new SerialRobbinsMonroUtil(realVectorUtil);
        boolean result = underStudy.needToContinue(iterations, minimumIterations, maximumIterations, parameter, autocorrelation);
        assertThat(result).isFalse();
    }
}
