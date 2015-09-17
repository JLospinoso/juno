package net.lospi.juno.estimation.ml;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.sim.SerialLikelihoodDerivativesSimulator;
import net.lospi.juno.model.Model;

import java.util.List;

public class SerialPhaseOneExecutor implements PhaseOneExecutor {
    protected static final int DEFAULT_BURN_IN = 25000;
    protected static final int DEFAULT_SAMPLES = 1000;
    protected static final int DEFAULT_SAMPLING_INTERVAL = 0;
    private final SerialLikelihoodDerivativesSimulator serialLikelihoodDerivativesSimulator;
    private final PhaseOneResultsBuilder phaseOneResultsBuilder;
    private final WeightMatrixCalculator weightMatrixCalculator;
    private int burnIn, samples, samplingInterval;

    public SerialPhaseOneExecutor(SerialLikelihoodDerivativesSimulator serialLikelihoodDerivativesSimulator,
                                  PhaseOneResultsBuilder phaseOneResultsBuilder, WeightMatrixCalculator weightMatrixCalculator) {
        this.serialLikelihoodDerivativesSimulator = serialLikelihoodDerivativesSimulator;
        this.phaseOneResultsBuilder = phaseOneResultsBuilder;
        this.weightMatrixCalculator = weightMatrixCalculator;
        samplingInterval = DEFAULT_SAMPLING_INTERVAL;
        burnIn = DEFAULT_BURN_IN;
        samples = DEFAULT_SAMPLES;
    }

    @Override
    public PhaseOneResults execute(Model model, Chain initialChain) {
        serialLikelihoodDerivativesSimulator.setChain(initialChain);
        serialLikelihoodDerivativesSimulator.setSamplingInterval(samplingInterval);
        serialLikelihoodDerivativesSimulator.burnIn(model, burnIn);
        List<LikelihoodDerivatives> likelihoodDerivatives = serialLikelihoodDerivativesSimulator.simulateLikelihoodDerivatives(model, samples);
        WeightMatrix weightMatrix = weightMatrixCalculator.calculate(likelihoodDerivatives, model.getAllEffects());
        return phaseOneResultsBuilder.with()
                .weightMatrix(weightMatrix)
                .build();
    }

    @Override
    public void setBurnIn(int burnIn) {
        this.burnIn = burnIn;
    }

    @Override
    public void setSamples(int samples) {
        this.samples = samples;
    }

    @Override
    public void setSamplingInterval(int samplingInterval) {
        this.samplingInterval = samplingInterval;
    }
}
