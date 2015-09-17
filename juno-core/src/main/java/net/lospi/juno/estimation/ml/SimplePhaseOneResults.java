package net.lospi.juno.estimation.ml;

public class SimplePhaseOneResults implements PhaseOneResults {
    private final String status;
    private final boolean successful;
    private final WeightMatrix weightMatrix;

    public SimplePhaseOneResults(String status, boolean successful, WeightMatrix weightMatrix) {
        this.status = status;
        this.successful = successful;
        this.weightMatrix = weightMatrix;
    }

    @Override
    public WeightMatrix getWeightMatrix() {
        return weightMatrix;
    }

    @Override
    public boolean isSuccessful() {
        return successful;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(String.format("%n  Successful: %b%n", successful));
        builder.append(String.format("  Status: %s%n", status));
        builder.append(String.format("%s%n", weightMatrix.toString()));
        return builder.toString();
    }
}
