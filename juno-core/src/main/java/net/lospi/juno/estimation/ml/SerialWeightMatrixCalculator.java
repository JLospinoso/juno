package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.calc.LikelihoodDerivativesCalculator;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.model.Effect;
import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.SafeObjectMatrix;

import java.util.List;

public class SerialWeightMatrixCalculator implements WeightMatrixCalculator {
    private final LikelihoodDerivativesCalculator likelihoodDerivativesCalculator;

    public SerialWeightMatrixCalculator(LikelihoodDerivativesCalculator likelihoodDerivativesCalculator) {
        this.likelihoodDerivativesCalculator = likelihoodDerivativesCalculator;
    }

    @Override
    public WeightMatrix calculate(List<LikelihoodDerivatives> linkLikelihoodDerivatives, List<Effect> effects) {
        LikelihoodDerivatives likelihoodDerivatives = likelihoodDerivativesCalculator.calculate(linkLikelihoodDerivatives, effects);
        int size = effects.size();
        ObjectMatrix information = likelihoodDerivatives.getInformation();
        double[][] resultArray = new double[size][size];
        for(int i=0; i<size; i++) {
            double weight = -1D / information.getEntry(i, i);
            resultArray[i][i] = weight;
        }
        ObjectMatrix result = new SafeObjectMatrix(resultArray, effects, effects);
        return new SimpleWeightMatrix(result, effects);
    }
}
