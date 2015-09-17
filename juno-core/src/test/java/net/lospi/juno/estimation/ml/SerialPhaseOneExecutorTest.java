package net.lospi.juno.estimation.ml;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.sim.SerialLikelihoodDerivativesSimulator;
import net.lospi.juno.model.Effect;
import net.lospi.juno.model.Model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class SerialPhaseOneExecutorTest {
    private int burnIn;
    private SerialLikelihoodDerivativesSimulator serialLikelihoodDerivativesSimulator;
    private PhaseOneResultsBuilder phaseOneResultsBuilder;
    private WeightMatrixCalculator weightMatrixCalculator;
    private int samples;
    private int samplingInterval;
    private Model model;
    private List<LikelihoodDerivatives> derivativesList;
    private WeightMatrix weightMatrix;
    private PhaseOneResultsBuilder.Stub stub;
    private PhaseOneResults expected;
    private Chain initialChain;
    private List<Effect> effects;

    @BeforeMethod
    public void setUp() {
        samples = 1;
        samplingInterval = 2;
        burnIn = 3;
        phaseOneResultsBuilder = mock(PhaseOneResultsBuilder.class);
        weightMatrixCalculator = mock(WeightMatrixCalculator.class);
        serialLikelihoodDerivativesSimulator = mock(SerialLikelihoodDerivativesSimulator.class);
        model = mock(Model.class);
        weightMatrix = mock(WeightMatrix.class);
        expected = mock(PhaseOneResults.class);
        stub = mock(PhaseOneResultsBuilder.Stub.class);
        derivativesList = mock(List.class);
        initialChain = mock(Chain.class);
        effects = mock(List.class);

        when(model.getAllEffects()).thenReturn(effects);
        when(weightMatrixCalculator.calculate(derivativesList, effects)).thenReturn(weightMatrix);
        when(phaseOneResultsBuilder.with()).thenReturn(stub);
        when(stub.weightMatrix(weightMatrix)).thenReturn(stub);
        when(stub.build()).thenReturn(expected);
    }

    public void execute() {
        when(serialLikelihoodDerivativesSimulator.simulateLikelihoodDerivatives(model, samples)).thenReturn(derivativesList);

        SerialPhaseOneExecutor underStudy = new SerialPhaseOneExecutor(serialLikelihoodDerivativesSimulator, phaseOneResultsBuilder, weightMatrixCalculator);
        underStudy.setSamplingInterval(samplingInterval);
        underStudy.setBurnIn(burnIn);
        underStudy.setSamples(samples);
        PhaseOneResults result = underStudy.execute(model, initialChain);

        assertThat(result).isEqualTo(expected);
        verify(serialLikelihoodDerivativesSimulator).setSamplingInterval(samplingInterval);
        verify(stub).weightMatrix(weightMatrix);
        verify(serialLikelihoodDerivativesSimulator).setChain(initialChain);
    }

    public void executeWithDefaults() {
        when(serialLikelihoodDerivativesSimulator.simulateLikelihoodDerivatives(model, SerialPhaseOneExecutor.DEFAULT_SAMPLES)).thenReturn(derivativesList);

        SerialPhaseOneExecutor underStudy = new SerialPhaseOneExecutor(serialLikelihoodDerivativesSimulator, phaseOneResultsBuilder, weightMatrixCalculator);
        PhaseOneResults result = underStudy.execute(model, initialChain);
        assertThat(result).isEqualTo(expected);

        verify(serialLikelihoodDerivativesSimulator).setSamplingInterval(SerialPhaseOneExecutor.DEFAULT_SAMPLING_INTERVAL);
        verify(serialLikelihoodDerivativesSimulator).burnIn(model, SerialPhaseOneExecutor.DEFAULT_BURN_IN);
        verify(stub).weightMatrix(weightMatrix);
        verify(serialLikelihoodDerivativesSimulator).setChain(initialChain);
    }
}
