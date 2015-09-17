/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.ml;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.model.Model;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SerialMaximumLikelihoodEstimator implements MaximumLikelihoodEstimator {
    private static final Log LOGGER = LogFactory.getLog(SerialMaximumLikelihoodEstimator.class);

    private MaximumLikelihoodResultsBuilder maximumLikelihoodResultsBuilder;
    private PhaseOneExecutor phaseOneExecutor;
    private PhaseTwoExecutor phaseTwoExecutor;
    private PhaseThreeExecutor phaseThreeExecutor;

    public SerialMaximumLikelihoodEstimator(MaximumLikelihoodResultsBuilder maximumLikelihoodResultsBuilder,
                                            PhaseOneExecutor phaseOneExecutor,
                                            PhaseTwoExecutor phaseTwoExecutor,
                                            PhaseThreeExecutor phaseThreeExecutor) {
        this.maximumLikelihoodResultsBuilder = maximumLikelihoodResultsBuilder;
        this.phaseOneExecutor = phaseOneExecutor;
        this.phaseTwoExecutor = phaseTwoExecutor;
        this.phaseThreeExecutor = phaseThreeExecutor;
    }

    @Override
    public MaximumLikelihoodResults estimate(Model model, Chain initialChain, RobbinsMonroPlan plan) {
        PhaseOneResults phaseOneResults = phaseOne(model, initialChain);
        if(!phaseOneResults.isSuccessful()) {
            return maximumLikelihoodResultsBuilder.with()
                    .phaseOneResults(phaseOneResults)
                    .build();
        }
        PhaseTwoResults phaseTwoResults = phaseTwo(model, phaseOneResults, plan);
        if(!phaseTwoResults.isSuccessful()) {
            return maximumLikelihoodResultsBuilder.with()
                    .phaseOneResults(phaseOneResults)
                    .phaseTwoResults(phaseTwoResults)
                    .build();
        }
        PhaseThreeResults phaseThreeResults = phaseThree(phaseTwoResults);
        return maximumLikelihoodResultsBuilder.with()
                .phaseOneResults(phaseOneResults)
                .phaseTwoResults(phaseTwoResults)
                .phaseThreeResults(phaseThreeResults)
                .build();
    }

    @Override
    public void setSamplingInterval(int interval) {
        phaseOneExecutor.setSamplingInterval(interval);
        phaseTwoExecutor.setSamplingInterval(interval);
        phaseThreeExecutor.setSamplingInterval(interval);
    }

    @Override
    public void setPhaseOneSample(int phaseOneSample) {
        phaseOneExecutor.setSamples(phaseOneSample);
    }

    @Override
    public void setPhaseOneBurnIn(int burnIn) {
        phaseOneExecutor.setBurnIn(burnIn);
    }

    @Override
    public void setDrawsPerPhaseTwoSample(int drawsPerPhaseTwoSample) {
        phaseTwoExecutor.setDrawsPerSample(drawsPerPhaseTwoSample);
    }

    @Override
    public void setPhaseThreeSamples(int phaseThreeSamples) {
        phaseThreeExecutor.setPhaseThreeIterations(phaseThreeSamples);
    }

    private PhaseOneResults phaseOne(Model model, Chain initialChain) {
        LOGGER.info("Executing phase one of ML Estimation.");
        PhaseOneResults phaseOneResults = phaseOneExecutor.execute(model, initialChain);
        LOGGER.info(String.format("Phase one complete: %s", phaseOneResults.toString()));
        return phaseOneResults;
    }

    private PhaseTwoResults phaseTwo(Model model, PhaseOneResults phaseOneResults, RobbinsMonroPlan plan) {
        LOGGER.info("Executing phase two of ML Estimation.");
        PhaseTwoResults phaseTwoResults = phaseTwoExecutor.execute(model, phaseOneResults.getWeightMatrix(), plan);
        LOGGER.info(String.format("Phase two complete: %s", phaseTwoResults.toString()));
        return phaseTwoResults;
    }

    private PhaseThreeResults phaseThree(PhaseTwoResults phaseTwoResults) {
        LOGGER.info("Executing phase three of ML Estimation.");
        phaseThreeExecutor.setModel(phaseTwoResults.getEstimatedModel());
        PhaseThreeResults phaseThreeResults = phaseThreeExecutor.execute();
        LOGGER.info(String.format("Phase three complete: %s", phaseThreeResults.toString()));
        return phaseThreeResults;
    }
}
