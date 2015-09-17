package net.lospi.juno.estimation.ml;

import net.lospi.juno.model.Effect;
import net.lospi.juno.model.ObjectMatrix;

import java.util.List;

public class PhaseOneResultsBuilder {
    public Stub with() {
        return new Stub();
    }

    public class Stub {
        private WeightMatrix weightMatrix;
        private String status;
        private boolean success;

        public Stub failure(String message) {
            status = message;
            success = false;
            return this;
        }

        public Stub weightMatrix(WeightMatrix weightMatrix) {
            this.weightMatrix = weightMatrix;
            return this;
        }

        PhaseOneResults build() {
            if(status == null && weightMatrix == null) {
                throw new IllegalStateException("You must set covariance or call failure before building results.");
            }
            if(weightMatrix != null) {
                setStatus();
            }
            return new SimplePhaseOneResults(status, success, weightMatrix);
        }

        private void setStatus() {
            StringBuilder statusBuilder = new StringBuilder();
            List<Effect> effectList = weightMatrix.getEffects();
            ObjectMatrix weightMatrixAsMatrix = this.weightMatrix.getWeights();
            success = true;
            for(int i=0; i<weightMatrixAsMatrix.getRowDimension(); i++) {
                if(weightMatrixAsMatrix.getEntry(i, i) < 0D) {
                    statusBuilder.append(String.format(" [-] WEIGHTS:  %5.2f %s%n", weightMatrixAsMatrix.getEntry(i, i), effectList.get(i)));
                    success = false;
                }
            }
            if(!success) {
                status = statusBuilder.toString();
            } else {
                status = String.format("Completed with positive weight diagonal");
            }
        }
    }
}
