package net.lospi.juno.model;

import net.lospi.juno.estimation.elements.CommonsLikelihoodDerivatives;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.stat.CachedExponential;
import net.lospi.juno.stat.CachedNaturalLogarithm;
import net.lospi.juno.stat.Statistics;

import java.util.List;

public class CommonsAlterSelectionDistribution implements AlterSelectionDistribution {
    private final CachedNaturalLogarithm naturalLogarithm;
    private final CachedExponential exponential;

    public CommonsAlterSelectionDistribution(CachedNaturalLogarithm naturalLogarithm, CachedExponential exponential) {
        this.naturalLogarithm = naturalLogarithm;
        this.exponential = exponential;
    }

    @Override
    public LikelihoodDerivatives calculate(Statistics statistics, Model model, String observation,
                                               boolean calculateScore, boolean calculateInformation) {
        List<String> possibleOutcomes = statistics.getOutcomes();
        List<AlterSelectionEffect> alterSelectionEffects = model.getAlterSelectionEffects();
        ObjectVector parameter = model.getAlterSelectionEffectsParameter();
        int parameterSize = parameter.getDimension();
        int outcomesSize = possibleOutcomes.size();
        double[] logLinkFunctionArray = new double[outcomesSize];
        for(int i=0; i<outcomesSize; i++) {
            String possibleOutcome = possibleOutcomes.get(i);
            ObjectVector statisticsForOutcome = statistics.getByOutcome(possibleOutcome);
            ObjectVector statisticsByParameter = parameter.ebeMultiply(statisticsForOutcome);
            logLinkFunctionArray[i] = sum(statisticsByParameter);
        }
        ObjectVector logLinkFunction = new SafeObjectVector(logLinkFunctionArray, possibleOutcomes);
        ObjectVector linkFunction = logLinkFunction.map(exponential);
        double sumOfLinkFunctions = sum(linkFunction);
        double logSumOfLinkFunctions = naturalLogarithm.apply(sumOfLinkFunctions);
        ObjectVector logProbabilities =  logLinkFunction.mapSubtract(logSumOfLinkFunctions);
        ObjectVector probabilities = logProbabilities.map(exponential);

        ObjectVector score = null;
        ObjectMatrix information = null;
        int indexOfObservation = statistics.indexOfOutcome(observation);
        double logLikelihood = logProbabilities.getEntry(indexOfObservation);
        if(calculateScore) {
            ObjectMatrix statisticMatrix = statistics.getRowsAsOutcomes();
            ObjectVector expectedStatistics = statisticMatrix.preMultiply(probabilities);
            ObjectMatrix candidateScores;
            if(calculateInformation) {
                double[][] expectedStatisticsArray = new double[outcomesSize][parameterSize];
                for(int i=0; i<outcomesSize; i++) {
                    expectedStatisticsArray[i] = expectedStatistics.asArray(alterSelectionEffects);
                }
                ObjectMatrix expectedStatisticsMatrix = new SafeObjectMatrix(expectedStatisticsArray, possibleOutcomes, alterSelectionEffects);
                candidateScores = statisticMatrix.subtract(expectedStatisticsMatrix);
                score = candidateScores.getRowVector(indexOfObservation);
                information = new SafeObjectMatrix(alterSelectionEffects, alterSelectionEffects);
                for(int i=0; i<outcomesSize; i++) {
                    ObjectVector candidateScore = candidateScores.getRowVector(i);
                    ObjectMatrix outerProduct = candidateScore.outerProduct(candidateScore);
                    outerProduct = outerProduct.scalarMultiply(probabilities.getEntry(i));
                    information =  information.add(outerProduct);
                }
            } else {
                ObjectVector observedStatistics =  statistics.getByOutcome(observation);
                score = observedStatistics.subtract(expectedStatistics);
            }
        }
        return new CommonsLikelihoodDerivatives(score, information, statistics.getEffects(), logLikelihood);
    }

    private double sum(ObjectVector vector) {
        double result = 0D;
        int vectorLength = vector.getDimension();
        for(int index=0; index<vectorLength; index++) {
            result += vector.getEntry(index);
        }
        return result;
    }
}
