package net.lospi.juno.estimation.elements;

import net.lospi.juno.model.*;

import java.util.LinkedList;
import java.util.List;

public class CompositeLikelihoodDerivativesBuilder {
    public Stub with() {
        return new Stub();
    }
    public class Stub {
        private List<LikelihoodDerivatives> derivativesList;

        public Stub derivatives(LikelihoodDerivatives likelihoodDerivatives) {
            if(derivativesList == null) {
                derivativesList = new LinkedList<LikelihoodDerivatives>();
            }
            derivativesList.add(likelihoodDerivatives);
            return this;
        }

        public LikelihoodDerivatives build() {
            if(derivativesList == null) {
                throw new IllegalStateException("You must add at least one likelihood derivative to the list");
            }
            List<Effect> effects = new LinkedList<Effect>();
            for(LikelihoodDerivatives derivatives : derivativesList) {
                effects.addAll(derivatives.getEffects());
            }
            double compositeLikelihood = 0D;
            int compositeEffectCount = effects.size();
            double[] compositeScoreArray = new double[compositeEffectCount];
            double[][] compositeInformationArray = new double[compositeEffectCount][compositeEffectCount];
            int compositeIndex = 0;
            for(LikelihoodDerivatives derivatives : derivativesList) {
                int effectCount = derivatives.getEffects().size();
                compositeLikelihood += derivatives.getLogLikelihood();
                ObjectVector score = derivatives.getScore();
                ObjectMatrix info = derivatives.getInformation();
                for(int rowIndex=0; rowIndex<effectCount; rowIndex++) {
                    compositeScoreArray[compositeIndex+rowIndex] = score.getEntry(rowIndex);
                    for(int columnIndex=0; columnIndex<effectCount; columnIndex++) {
                        compositeInformationArray[compositeIndex+rowIndex][compositeIndex+columnIndex] = info.getEntry(rowIndex, columnIndex);
                    }
                }
                compositeIndex += effectCount;
            }
            ObjectVector compositeScore = new SafeObjectVector(compositeScoreArray, effects);
            ObjectMatrix compositeInformation = new SafeObjectMatrix(compositeInformationArray, effects, effects);
            return new CommonsLikelihoodDerivatives(compositeScore, compositeInformation, effects, compositeLikelihood);
        }
    }
}
