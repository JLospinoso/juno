package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.sim.SerialScoreSimulator;
import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.Model;
import net.lospi.juno.model.SafeObjectMatrix;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class SerialPhaseTwoExecutorTest {
    private WeightMatrix weightMatrix;
    private SerialScoreSimulator scoreSimulator;
    private RobbinsMonro robbinsMonro;
    private PhaseTwoResultsBuilder phaseTwoResultsBuilder;
    private RobbinsMonroPlan plan;
    private int samplingInterval;
    private Model model;
    RobbinsMonroResult robbinsMonroResult;
    private PhaseTwoResultsBuilder.Stub stub;
    private PhaseTwoResults expected;
    private String status;
    private int drawsPerSample;

    @BeforeMethod
    public void setUp() {
        robbinsMonroResult = mock(RobbinsMonroResult.class);
        ObjectMatrix weights = mock(SafeObjectMatrix.class);
        samplingInterval = 100;
        drawsPerSample = 200;
        plan = mock(RobbinsMonroPlan.class);
        scoreSimulator = mock(SerialScoreSimulator.class);
        robbinsMonro = mock(RobbinsMonro.class);
        model = mock(Model.class);
        phaseTwoResultsBuilder = mock(PhaseTwoResultsBuilder.class);
        weightMatrix = mock(WeightMatrix.class);
        stub = mock(PhaseTwoResultsBuilder.Stub.class);
        expected = mock(PhaseTwoResults.class);
        status = "foo";

        when(weightMatrix.getWeights()).thenReturn(weights);
        when(robbinsMonro.optimize(eq(scoreSimulator), eq(weights), eq(plan))).thenReturn(robbinsMonroResult);
        when(robbinsMonroResult.getStatus()).thenReturn(status);
        when(phaseTwoResultsBuilder.with()).thenReturn(stub);
        when(scoreSimulator.getModel()).thenReturn(model);
        when(stub.model(model)).thenReturn(stub);
        when(stub.failure(status)).thenReturn(stub);
        when(stub.build()).thenReturn(expected);
    }

    public void succesfulRobbinsMonro() {
        when(robbinsMonroResult.isSuccessful()).thenReturn(true);

        SerialPhaseTwoExecutor underStudy = new SerialPhaseTwoExecutor(phaseTwoResultsBuilder, robbinsMonro, scoreSimulator);
        PhaseTwoResults result = underStudy.execute(model, weightMatrix, plan);
        assertThat(result).isEqualTo(expected);

        verify(scoreSimulator).setSamplingInterval(0);
        verify(stub).model(model);
    }

    public void succesfulRobbinsMonroSpecialSamplingInterval() {
        when(robbinsMonroResult.isSuccessful()).thenReturn(true);

        SerialPhaseTwoExecutor underStudy = new SerialPhaseTwoExecutor(phaseTwoResultsBuilder, robbinsMonro, scoreSimulator);
        underStudy.setSamplingInterval(samplingInterval);
        PhaseTwoResults result = underStudy.execute(model, weightMatrix, plan);
        assertThat(result).isEqualTo(expected);

        verify(scoreSimulator).setSamplingInterval(samplingInterval);
        verify(stub).model(model);
    }

    public void unsuccesfulRobbinsMonro() {
        when(robbinsMonroResult.isSuccessful()).thenReturn(false);

        SerialPhaseTwoExecutor underStudy = new SerialPhaseTwoExecutor(phaseTwoResultsBuilder, robbinsMonro, scoreSimulator);
        PhaseTwoResults result = underStudy.execute(model, weightMatrix, plan);
        assertThat(result).isEqualTo(expected);

        verify(scoreSimulator).setSamplingInterval(0);
        verify(stub).failure(status);
    }


    public void succesfulRobbinsMonroSpecialDrawsPerSample() {
        when(robbinsMonroResult.isSuccessful()).thenReturn(false);

        SerialPhaseTwoExecutor underStudy = new SerialPhaseTwoExecutor(phaseTwoResultsBuilder, robbinsMonro, scoreSimulator);
        underStudy.setDrawsPerSample(drawsPerSample);
        PhaseTwoResults result = underStudy.execute(model, weightMatrix, plan);
        assertThat(result).isEqualTo(expected);

        verify(scoreSimulator).setDrawsPerSample(drawsPerSample);
        verify(stub).failure(status);
    }
}
