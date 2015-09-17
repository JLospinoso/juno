package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.sim.SerialScoreSimulator;
import net.lospi.juno.model.Model;

public class SerialPhaseTwoExecutor implements PhaseTwoExecutor {
    private final PhaseTwoResultsBuilder phaseTwoResultsBuilder;
    private int samplingInterval;
    private final RobbinsMonro robbinsMonro;
    private final SerialScoreSimulator scoreSimulator;

    public SerialPhaseTwoExecutor(PhaseTwoResultsBuilder phaseTwoResultsBuilder, RobbinsMonro robbinsMonro,
                                  SerialScoreSimulator scoreSimulator) {
        this.phaseTwoResultsBuilder = phaseTwoResultsBuilder;
        this.robbinsMonro = robbinsMonro;
        this.scoreSimulator = scoreSimulator;
        samplingInterval = 0;
    }

    @Override
    public PhaseTwoResults execute(Model model, WeightMatrix weightMatrix, RobbinsMonroPlan plan) {
        scoreSimulator.setSamplingInterval(samplingInterval);
        scoreSimulator.setModel(model);
        RobbinsMonroResult result = robbinsMonro.optimize(scoreSimulator, weightMatrix.getWeights(), plan);
        Model estimatedModel = scoreSimulator.getModel();
        if(!result.isSuccessful()) {
            phaseTwoResultsBuilder.with()
            .failure(result.getStatus())
            .build();
        }
        return phaseTwoResultsBuilder.with()
                .model(estimatedModel)
                .build();
    }

    @Override
    public void setSamplingInterval(int samplingInterval) {
        this.samplingInterval = samplingInterval;
    }

    @Override
    public void setDrawsPerSample(int drawsPerSample) {
        this.scoreSimulator.setDrawsPerSample(drawsPerSample);
    }
}
