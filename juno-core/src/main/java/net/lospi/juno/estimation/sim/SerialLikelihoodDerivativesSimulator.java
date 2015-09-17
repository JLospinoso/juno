package net.lospi.juno.estimation.sim;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.calc.LikelihoodDerivativesCalculator;
import net.lospi.juno.estimation.calc.RateLikelihoodDerivativesCalculator;
import net.lospi.juno.estimation.calc.SimpleChainLikelihoodUpdater;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.elements.CompositeLikelihoodDerivativesBuilder;
import net.lospi.juno.model.Model;

import java.util.ArrayList;
import java.util.List;

public class SerialLikelihoodDerivativesSimulator implements LikelihoodDerivativesSimulator {
    protected static final int DEFAULT_SAMPLING_INTERVAL = 0;
    private final ChainSimulator chainSimulator;
    private final SimpleChainLikelihoodUpdater simpleChainLikelihoodUpdater;
    private final LikelihoodDerivativesCalculator likelihoodDerivativesCalculator;
    private final CompositeLikelihoodDerivativesBuilder compositeLikelihoodDerivativesBuilder;
    private final RateLikelihoodDerivativesCalculator rateLikelihoodDerivativesCalculator;
    private int samplingInterval;

    public SerialLikelihoodDerivativesSimulator(ChainSimulator chainSimulator, SimpleChainLikelihoodUpdater simpleChainLikelihoodUpdater,
                                                LikelihoodDerivativesCalculator likelihoodDerivativesCalculator,
                                                CompositeLikelihoodDerivativesBuilder compositeLikelihoodDerivativesBuilder,
                                                RateLikelihoodDerivativesCalculator rateLikelihoodDerivativesCalculator) {
        this.chainSimulator = chainSimulator;
        this.simpleChainLikelihoodUpdater = simpleChainLikelihoodUpdater;
        this.likelihoodDerivativesCalculator = likelihoodDerivativesCalculator;
        this.compositeLikelihoodDerivativesBuilder = compositeLikelihoodDerivativesBuilder;
        this.rateLikelihoodDerivativesCalculator = rateLikelihoodDerivativesCalculator;
        samplingInterval = DEFAULT_SAMPLING_INTERVAL;
    }

    @Override
    public List<LikelihoodDerivatives> simulateLikelihoodDerivatives(Model model, int samples) {
        checkChainInitialization();
        chainSimulator.setModel(model);
        int samplesTaken = 0;
        List<LikelihoodDerivatives> simulatedDerivatives = new ArrayList<LikelihoodDerivatives>(samples);
        while (samplesTaken < samples) {
            advanceChain();
            Chain state = chainSimulator.getChain();
            simpleChainLikelihoodUpdater.updateInformation(state, model);
            List<LikelihoodDerivatives> linkDerivativeList = state.getLinkLikelihoodDerivatives();
            LikelihoodDerivatives alterSelectionDerivatives = likelihoodDerivativesCalculator.calculate(linkDerivativeList, model.getAlterSelectionEffects());
            LikelihoodDerivatives rateDerivatives = rateLikelihoodDerivativesCalculator.calculate(state, model);
            LikelihoodDerivatives compositeDerivatives = compositeLikelihoodDerivativesBuilder.with()
                    .derivatives(rateDerivatives)
                    .derivatives(alterSelectionDerivatives)
                    .build();
            simulatedDerivatives.add(compositeDerivatives);
            samplesTaken++;
        }
        return simulatedDerivatives;
    }

    @Override
    public void burnIn(Model model, int iterations) {
        checkChainInitialization();
        chainSimulator.setModel(model);
        int samplesTaken = 0;
        while (samplesTaken < iterations) {
            chainSimulator.advance();
            samplesTaken++;
        }
    }

    @Override
    public void setChain(Chain chain) {
        chainSimulator.setChain(chain);
    }

    @Override
    public void setSamplingInterval(int samplingInterval) {
        this.samplingInterval = samplingInterval;
    }

    private void checkChainInitialization() {
        if(!chainSimulator.isChainSet()) {
            throw new IllegalStateException("You must set an initial chain before simulating.");
        }
    }

    private void advanceChain() {
        int samplesBurned = 0;
        while(samplesBurned <= samplingInterval){
            chainSimulator.advance();
            samplesBurned++;
        }
    }
}
