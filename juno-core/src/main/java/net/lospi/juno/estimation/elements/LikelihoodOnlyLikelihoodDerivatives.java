package net.lospi.juno.estimation.elements;

import net.lospi.juno.model.SafeObjectMatrix;
import net.lospi.juno.model.SafeObjectVector;

import java.util.List;

public class LikelihoodOnlyLikelihoodDerivatives<T> implements LikelihoodDerivatives {
    private final double logLikelihood;

    public LikelihoodOnlyLikelihoodDerivatives(double logLikelihood) {
        this.logLikelihood = logLikelihood;
    }

    @Override
    public double getLogLikelihood() {
        return logLikelihood;
    }

    @Override
    public SafeObjectVector getScore() {
        throw new IllegalStateException();
    }

    @Override
    public SafeObjectMatrix getInformation() {
        throw new IllegalStateException();
    }

    @Override
    public List<T> getEffects() {
        throw new IllegalStateException();
    }
}
