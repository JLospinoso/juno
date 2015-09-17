package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.model.Effect;

import java.util.List;

public interface WeightMatrixCalculator {
    WeightMatrix calculate(List<LikelihoodDerivatives> likelihoodDerivatives, List<Effect> effects);
}
