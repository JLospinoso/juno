package net.lospi.juno.estimation.ml;

import net.lospi.juno.model.ObjectVector;

public class RobbinsMonroResult {
    private final ObjectVector solution;
    private final String status;
    private final boolean success;

    public RobbinsMonroResult(ObjectVector solution, String status, boolean success) {
        this.solution = solution;
        this.status = status;
        this.success = success;
    }

    public ObjectVector getSolution() {
        return solution;
    }

    public boolean isSuccessful() {
        return success;
    }

    public String getStatus() {
        return status;
    }
}
