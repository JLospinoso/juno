package net.lospi.juno.estimation.ml;


import net.lospi.juno.model.Model;

public class PhaseTwoResultsBuilder {
    public Stub with() {
        return new Stub();
    }

    public class Stub {
        private String status;
        private boolean success;
        private Model model;

        public Stub failure(String message) {
            status = message;
            success = false;
            return this;
        }

        public Stub model(Model model) {
            this.status = "Completed";
            this.success = true;
            this.model = model;
            return this;
        }

        PhaseTwoResults build() {
            if(status == null) {
                throw new IllegalStateException("You must set model or call failure before building results.");
            }
            return new PhaseTwoResults(model, success, status);
        }
    }
}
