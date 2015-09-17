package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.elements.ConvergenceRatio;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.elements.ParameterCovariance;
import net.lospi.juno.model.Effect;
import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.ObjectVector;
import net.lospi.juno.model.SafeObjectVector;

import java.util.List;

public class ConvergenceRatioCalculator {
    public ConvergenceRatioCalculator() {

    }
    public ConvergenceRatio calculate(List<LikelihoodDerivatives> simulatedDerivatives, ParameterCovariance covariance) {
        ObjectMatrix covMatrix = covariance.getCovariance();
        List<Effect> effects = covariance.getEffects();
        ObjectVector score = new SafeObjectVector(effects);
        for(LikelihoodDerivatives derivatives : simulatedDerivatives) {
            score = score.add(derivatives.getScore());
        }
        score = score.mapMultiply(1D / (double) simulatedDerivatives.size());
        ObjectVector ratioVector = covMatrix.preMultiply(score);
        ratioVector.mapMultiply(-1D);
        return new ConvergenceRatio(ratioVector, effects);
    }
}
