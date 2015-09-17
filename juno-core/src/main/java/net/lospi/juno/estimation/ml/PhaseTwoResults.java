/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.ml;

import net.lospi.juno.model.Model;

public class PhaseTwoResults {
    private final Model estimatedModel;
    private final boolean successful;
    private final String status;

    public PhaseTwoResults(Model estimatedModel, boolean successful, String status) {
        this.estimatedModel = estimatedModel;
        this.successful = successful;
        this.status = status;
    }

    public Model getEstimatedModel() {
        return this.estimatedModel;
    }

    public String getStatus() {
        return status;
    }

    public boolean isSuccessful() {
        return successful;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(String.format("%n  Successful: %b%n", successful));
        builder.append(String.format("  Status: %s%n", status));
        builder.append(String.format("%s", estimatedModel.toString()));
        return builder.toString();
    }
}
