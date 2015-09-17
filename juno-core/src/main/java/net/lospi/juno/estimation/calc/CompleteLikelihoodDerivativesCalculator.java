package net.lospi.juno.estimation.calc;

import net.lospi.juno.estimation.elements.CommonsLikelihoodDerivatives;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.model.*;

import java.util.List;

public class CompleteLikelihoodDerivativesCalculator implements LikelihoodDerivativesCalculator<AlterSelectionEffect> {
    @Override
    public LikelihoodDerivatives calculate(List<LikelihoodDerivatives> completeDataDerivatives, List<AlterSelectionEffect> effects) {
        double logLikelihood = 0D;
        ObjectVector score =  new SafeObjectVector(effects);
        ObjectMatrix scoreCovariance = new SafeObjectMatrix(effects, effects);
        ObjectMatrix completeInformation = new SafeObjectMatrix(effects, effects);
        if(completeDataDerivatives.isEmpty()) {
            throw new IllegalArgumentException("You must have at least one derivatives sample.");
        }
        for(LikelihoodDerivatives likelihoodDerivatives : completeDataDerivatives) {
            ObjectVector linkScore = likelihoodDerivatives.getScore();
            logLikelihood += likelihoodDerivatives.getLogLikelihood();
            score = score.add(linkScore);
            scoreCovariance =  scoreCovariance.add(linkScore.outerProduct(linkScore));
            completeInformation = completeInformation.add(likelihoodDerivatives.getInformation());
        }
        double sampleSize = completeDataDerivatives.size();
        double inverseSampleSize = 1D/sampleSize;
        logLikelihood /= sampleSize;
        score = score.mapDivide(sampleSize);
        completeInformation = completeInformation.scalarMultiply(inverseSampleSize);
        return new CommonsLikelihoodDerivatives(score, completeInformation, effects, logLikelihood);
    }
}
