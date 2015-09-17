/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.ml;

public class MaximumLikelihoodResultsBuilder {
    public Stub with() {
        return new Stub();
    }
    public class Stub {
        private PhaseOneResults phaseOneResults;
        private PhaseTwoResults phaseTwoResults;
        private PhaseThreeResults phaseThreeResults;
        private boolean successful;
        private Stub() {

        }

        public Stub phaseOneResults(PhaseOneResults phaseOneResults) {
            this.phaseOneResults = phaseOneResults;
            successful = phaseOneResults.isSuccessful();
            return this;
        }

        public Stub phaseTwoResults(PhaseTwoResults phaseTwoResults) {
            this.phaseTwoResults = phaseTwoResults;
            successful = phaseTwoResults.isSuccessful();
            return this;
        }

        public Stub phaseThreeResults(PhaseThreeResults phaseThreeResults) {
            this.phaseThreeResults = phaseThreeResults;
            successful = phaseThreeResults.isSuccessful();
            return this;
        }

        public MaximumLikelihoodResults build() {
            if(phaseOneResults == null) {
                throw new IllegalStateException("You must set PhaseOneResults.");
            }
            if(phaseTwoResults == null && successful) {
                throw new IllegalStateException("You must set PhaseTwoResults.");
            }
            if(phaseThreeResults == null && successful) {
                throw new IllegalStateException("You must set PhaseThreeResults.");
            }
            return new SimpleMaximumLikelihoodResults(successful, phaseOneResults, phaseTwoResults, phaseThreeResults);
        }
    }
}
