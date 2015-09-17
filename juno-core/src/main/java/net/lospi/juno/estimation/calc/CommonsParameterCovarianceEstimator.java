package net.lospi.juno.estimation.calc;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.estimation.elements.CommonsParameterCovariance;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.elements.ParameterCovariance;
import net.lospi.juno.model.Effect;
import net.lospi.juno.model.ObjectMatrix;

import java.util.List;

public class CommonsParameterCovarianceEstimator implements ParameterCovarianceEstimator {

    public CommonsParameterCovarianceEstimator() {

    }

    @Override
    public ParameterCovariance calculate(List<LikelihoodDerivatives> simulatedDerivatives) {
        ObjectMatrix information = null;
        List<Effect> effects = null;
        if(simulatedDerivatives.isEmpty()) {
            throw new IllegalArgumentException("simulatedDerivatives must contain elements");
        }
        double inverseCount = 1D / simulatedDerivatives.size();
        for(LikelihoodDerivatives derivatives : simulatedDerivatives) {
            if(null == information) {
                information = derivatives.getInformation();
                effects = ImmutableList.copyOf(derivatives.getEffects());
            } else {
                information = information.add(derivatives.getInformation());
            }
        }
        information = information.scalarMultiply(inverseCount);
        ObjectMatrix informationInverse = information.getInverse();
        return new CommonsParameterCovariance(informationInverse, effects);
    }
}
