package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.elements.ConvergenceRatio;
import net.lospi.juno.estimation.elements.ParameterCovariance;
import net.lospi.juno.model.Effect;
import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.ObjectVector;

import java.util.List;

public class PhaseThreeResultsBuilder {
    public static final double CONVERGENCE_RATIO_MAXIMUM = 0.15;

    public Stub with() {
        return new Stub();
    }

    public class Stub {
        private ParameterCovariance parameterCovariance;
        private String status;
        private boolean success;
        private ConvergenceRatio convergenceRatio;

        public Stub failure(String message) {
            status = message;
            success = false;
            return this;
        }

        public Stub covariance(ParameterCovariance parameterCovariance) {
            this.parameterCovariance = parameterCovariance;
            return this;
        }

        public Stub convergenceRatio(ConvergenceRatio convergenceRatio) {
            this.convergenceRatio = convergenceRatio;
            return this;
        }

        PhaseThreeResults build() {
            if (status != null) {
                return new SimplePhaseThreeResults(status, success, parameterCovariance, convergenceRatio);
            }

            if(parameterCovariance == null || convergenceRatio == null) {
                throw new IllegalStateException("You must set both covariance and convergence.");
            }

            setStatus();
            return new SimplePhaseThreeResults(status, success, parameterCovariance, convergenceRatio);
        }

        private void setStatus() {
            StringBuilder statusBuilder = new StringBuilder();
            ObjectVector convergenceVector = convergenceRatio.getTRatios();
            List<Effect> effectList = convergenceRatio.getEffects();
            ObjectMatrix covarianceMatrix = parameterCovariance.getCovariance();
            success = true;
            for(int i=0; i<convergenceVector.getDimension(); i++) {
                if(Math.abs(convergenceVector.getEntry(i)) >  CONVERGENCE_RATIO_MAXIMUM) {
                    statusBuilder.append(String.format(" [-] CONVERGENCE: %5.2f %s%n", convergenceVector.getEntry(i), effectList.get(i)));
                    success = false;
                }
                if(covarianceMatrix.getEntry(i, i) < 0D) {
                    statusBuilder.append(String.format(" [-] COVARIANCE:  %5.2f %s%n", convergenceVector.getEntry(i), effectList.get(i)));
                    success = false;
                }
            }
            if(!success) {
                status = statusBuilder.toString();
            } else {
                status = String.format("Completed with positive covariance diagonal and all convergence ratios < %4.3f", CONVERGENCE_RATIO_MAXIMUM);
            }
        }
    }
}
