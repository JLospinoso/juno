package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.sim.ParametricProcess;
import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.ObjectVector;
import net.lospi.juno.model.SafeObjectMatrix;
import net.lospi.juno.model.SafeObjectVector;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class SerialRobbinsMonroTest {
    private SerialRobbinsMonroPhaseExecutor phaseExecutor;
    private RobbinsMonroResultsBuilder robbinsMonroResultsBuilder;
    private ObjectMatrix weight;
    private RobbinsMonroPlan plan;
    private SafeObjectVector root;
    private ParametricProcess simulator;
    private RobbinsMonroResultsBuilder.Stub stub;
    private RobbinsMonroResult expected;
    private ObjectVector solution0, solution1;
    private double gain0, gain1;
    private int minIterations0, minIterations1, maxIterations0, maxIterations1;
    private SerialRobbinsMonroUtil util;

    @BeforeMethod
    public void setUp() {
        plan = mock(RobbinsMonroPlan.class);
        root = mock(SafeObjectVector.class);
        simulator = mock(ParametricProcess.class);
        robbinsMonroResultsBuilder = mock(RobbinsMonroResultsBuilder.class);
        phaseExecutor = mock(SerialRobbinsMonroPhaseExecutor.class);
        weight = mock(SafeObjectMatrix.class);
        expected = mock(RobbinsMonroResult.class);
        solution0 = mock(SafeObjectVector.class);
        solution1 = mock(SafeObjectVector.class);
        stub = mock(RobbinsMonroResultsBuilder.Stub.class);
        util = mock(SerialRobbinsMonroUtil.class);
        gain0 = 2D;
        gain1 = 1D;
        minIterations0 = 10;
        minIterations1 = 20;
        maxIterations0 = 30;
        maxIterations1 = 40;

        when(util.parametersAreHealthy(solution0)).thenReturn(true);
        when(util.parametersAreHealthy(solution1)).thenReturn(true);

        when(robbinsMonroResultsBuilder.with()).thenReturn(stub);
        when(stub.solution(solution1)).thenReturn(stub);
        when(stub.failure(anyString())).thenReturn(stub);
        when(stub.build()).thenReturn(expected);

        when(plan.phaseCount()).thenReturn(2);
        when(plan.gain(0)).thenReturn(gain0);
        when(plan.gain(1)).thenReturn(gain1);
        when(plan.minimumIterations(0)).thenReturn(minIterations0);
        when(plan.minimumIterations(1)).thenReturn(minIterations1);
        when(plan.maximumIterations(0)).thenReturn(maxIterations0);
        when(plan.maximumIterations(1)).thenReturn(maxIterations1);

        when(phaseExecutor.executeSubPhase(eq(simulator), eq(weight), any(SafeObjectVector.class), eq(gain0), eq(minIterations0), eq(maxIterations0))).thenReturn(solution0);
        when(phaseExecutor.executeSubPhase(eq(simulator), eq(weight), any(SafeObjectVector.class), eq(gain1), eq(minIterations1), eq(maxIterations1))).thenReturn(solution1);
    }

    public void optimizeDefaultRoot() {
        SerialRobbinsMonro underStudy = new SerialRobbinsMonro(robbinsMonroResultsBuilder, phaseExecutor, util);
        RobbinsMonroResult result = underStudy.optimize(simulator, weight, plan);


        assertThat(result).isEqualTo(expected);
        verify(stub).solution(solution1);
    }

    public void optimizeWithSetRoot() {
        SerialRobbinsMonro underStudy = new SerialRobbinsMonro(robbinsMonroResultsBuilder, phaseExecutor, util);
        underStudy.setRoot(root);
        RobbinsMonroResult result = underStudy.optimize(simulator, weight, plan);


        assertThat(result).isEqualTo(expected);
        verify(phaseExecutor).executeSubPhase(simulator, weight, root, gain0, minIterations0, maxIterations0);
        verify(phaseExecutor).executeSubPhase(simulator, weight, root, gain1, minIterations1, maxIterations1);
        verify(stub).solution(solution1);
    }

    public void dieWithUnhealthyParameter() {
        SerialRobbinsMonro underStudy = new SerialRobbinsMonro(robbinsMonroResultsBuilder, phaseExecutor, util);

        when(util.parametersAreHealthy(solution0)).thenReturn(false);

        RobbinsMonroResult result = underStudy.optimize(simulator, weight, plan);
        assertThat(result).isEqualTo(expected);
        verify(stub).failure(anyString());
    }
}
