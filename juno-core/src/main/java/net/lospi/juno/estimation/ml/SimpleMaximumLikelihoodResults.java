/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.ml;

public class SimpleMaximumLikelihoodResults implements MaximumLikelihoodResults {
    private final boolean successful;
    private final PhaseOneResults phaseOneResults;
    private final PhaseTwoResults phaseTwoResults;
    private final PhaseThreeResults phaseThreeResults;

    public SimpleMaximumLikelihoodResults(boolean successful, PhaseOneResults phaseOneResults,
                                          PhaseTwoResults phaseTwoResults, PhaseThreeResults phaseThreeResults) {
        this.successful = successful;
        this.phaseOneResults = phaseOneResults;
        this.phaseTwoResults = phaseTwoResults;
        this.phaseThreeResults = phaseThreeResults;
    }

    @Override
    public boolean isSuccessful() {
        return successful;
    }

    @Override
    public PhaseThreeResults getPhaseThreeResults() {
        return phaseThreeResults;
    }

    @Override
    public PhaseTwoResults getPhaseTwoResults() {
        return phaseTwoResults;
    }

    @Override
    public PhaseOneResults getPhaseOneResults() {
        return phaseOneResults;
    }
}
