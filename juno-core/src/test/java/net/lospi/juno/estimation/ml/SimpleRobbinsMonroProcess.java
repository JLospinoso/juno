package net.lospi.juno.estimation.ml;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.estimation.sim.ParametricProcess;
import net.lospi.juno.model.ObjectVector;
import net.lospi.juno.model.SafeObjectVector;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.stat.StatUtils;

import java.util.List;

public class SimpleRobbinsMonroProcess implements ParametricProcess {
    private final int samples;
    private final RandomGenerator randomGenerator;
    private static final double MINIMUM_STD_VAR = 0.01D;
    private NormalDistribution distribution;
    public static final List<String> INDEX = ImmutableList.of("Mean", "LogVariance");

    public SimpleRobbinsMonroProcess(int samples, ObjectVector initialParameter, RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
        setParameters(initialParameter);
        this.samples = samples;
    }

    @Override
    public ObjectVector sample() {
        double[] draw = distribution.sample(samples);
        double sampleMean = StatUtils.mean(draw);
        double sampleVariance = StatUtils.variance(draw);
        double logSampleVariance = Math.log(sampleVariance);
        return new SafeObjectVector(new double[] { sampleMean, logSampleVariance }, INDEX);
    }

    @Override
    public ObjectVector addToAndSetParameters(ObjectVector step) {
        ObjectVector parameters = getParameters().add(step);
        double mean = parameters.getEntry(0);
        double variance = Math.exp(parameters.getEntry(1));
        variance = Math.max(MINIMUM_STD_VAR, variance);
        double stdDev = Math.sqrt(variance);
        distribution = new NormalDistribution(randomGenerator, mean, stdDev);
        return parameters;
    }

    @Override
    public void setParameters(ObjectVector parameters) {
        double mean = parameters.getEntry(0);
        double variance = Math.exp(parameters.getEntry(1));
        variance = Math.max(MINIMUM_STD_VAR, variance);
        double stdDev =  Math.sqrt(variance);
        distribution = new NormalDistribution(randomGenerator, mean, stdDev);
    }

    private ObjectVector getParameters() {
        double mean = distribution.getMean();
        double stdDeviation = distribution.getStandardDeviation();
        double variance = Math.pow(stdDeviation, 2);
        double logVariance = Math.log(variance);
        return new SafeObjectVector(new double[] { mean, logVariance}, INDEX);
    }

    @Override
    public int parameterSize() {
        return INDEX.size();
    }

    @Override
    public List getIndex() {
        return INDEX;
    }
}
