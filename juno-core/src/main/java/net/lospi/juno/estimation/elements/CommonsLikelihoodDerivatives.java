package net.lospi.juno.estimation.elements;

import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.ObjectVector;
import net.lospi.juno.model.SafeObjectMatrix;
import net.lospi.juno.model.SafeObjectVector;

import java.util.List;

public class CommonsLikelihoodDerivatives<T> implements LikelihoodDerivatives {
    private final List<T> effects;
    private final ObjectMatrix information;
    private final ObjectVector score;
    private final double logLikelihood;

    public CommonsLikelihoodDerivatives(ObjectVector score, ObjectMatrix information, List<T> effects, double logLikelihood) {
        this.effects = effects;
        this.information = information;
        this.score = score;
        this.logLikelihood = logLikelihood;
    }

    @Override
    public double getLogLikelihood() {
        return logLikelihood;
    }

    @Override
    public ObjectVector getScore() {
        return score;
    }

    @Override
    public ObjectMatrix getInformation() {
        return information;
    }

    @Override
    public List<T> getEffects() {
        return effects;
    }
}
