package net.lospi.juno.estimation.calc;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.elements.CommonsLikelihoodDerivatives;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.model.*;
import net.lospi.juno.stat.CachedLogFactorial;
import net.lospi.juno.stat.CachedNaturalLogarithm;

import java.util.List;

public class RateLikelihoodDerivativesCalculator {
    private final CachedLogFactorial logFactorial;
    private final CachedNaturalLogarithm naturalLogarithm;

    public RateLikelihoodDerivativesCalculator(CachedLogFactorial logFactorial, CachedNaturalLogarithm naturalLogarithm) {
        this.logFactorial = logFactorial;
        this.naturalLogarithm = naturalLogarithm;
    }

    public LikelihoodDerivatives<RateEffect> calculate(Chain chain, Model model) {
        //TODO: size/global rate need to get the axe.. multiple aspects?
        int chainSize = chain.getSize();
        double rateParameter = model.globalRate();
        double logLikelihood = (double) chainSize * naturalLogarithm.apply(rateParameter) - rateParameter - logFactorial.apply(chainSize);
        double score = (double) chainSize / rateParameter - 1D;
        double info = -1D * (double) chainSize / rateParameter / rateParameter;
        List<RateEffect> rateEffects = model.getRateEffects();
        SafeObjectVector scoreVector = new SafeObjectVector(new double[] { score }, rateEffects);
        SafeObjectMatrix infoMatrix = new SafeObjectMatrix(new double[][] { new double[] { info } }, rateEffects, rateEffects);
        return new CommonsLikelihoodDerivatives<RateEffect>(scoreVector, infoMatrix, model.getRateEffects(), logLikelihood);
    }
}
