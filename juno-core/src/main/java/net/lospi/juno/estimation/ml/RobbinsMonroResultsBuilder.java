package net.lospi.juno.estimation.ml;

import net.lospi.juno.model.ObjectVector;

public class RobbinsMonroResultsBuilder {
    public Stub with() {
        return new Stub();
    }

    public class Stub {
        private String status;
        private boolean success;
        private ObjectVector solution;

        public Stub failure(String message) {
            status = message;
            success = false;
            return this;
        }

        public Stub solution(ObjectVector solution) {
            status = "Solution found";
            success = true;
            this.solution = solution;
            return this;
        }

        RobbinsMonroResult build() {
            if(status == null) {
                throw new IllegalStateException("You must set solution or call failure before building results.");
            }
            return new RobbinsMonroResult(solution, status, success);
        }
    }
}
