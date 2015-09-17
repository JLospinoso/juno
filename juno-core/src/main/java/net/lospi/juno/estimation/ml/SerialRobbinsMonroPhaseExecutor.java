package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.sim.ParametricProcess;
import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.ObjectVector;
import net.lospi.juno.model.SafeObjectVector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SerialRobbinsMonroPhaseExecutor {
    protected static final int LOGGER_UPDATE_INTERVAL = 20;
    private static final Log LOG = LogFactory.getLog(SerialRobbinsMonroPhaseExecutor.class);
    private final SerialRobbinsMonroUtil util;
    private final StreamingRealVectorCalculator streamingRealVectorCalculator;
    private ObjectVector averageParameter;
    private int parameterSize, iteration;

    public SerialRobbinsMonroPhaseExecutor(SerialRobbinsMonroUtil util,
                                           StreamingRealVectorCalculator streamingRealVectorCalculator) {
        this.util = util;
        this.streamingRealVectorCalculator = streamingRealVectorCalculator;
    }

    public ObjectVector executeSubPhase(ParametricProcess simulator, ObjectMatrix weight, ObjectVector root, double gain,
                                      int minimumIterations, int maximumIterations) {
        boolean continueExecuting;
        initializeFields(simulator);
        do {
            ObjectVector step = util.sampleNextStep(simulator, weight, root, gain);
            ObjectVector currentParameter = simulator.addToAndSetParameters(step);
            ObjectVector autocorrelation = streamingRealVectorCalculator.calculateAutocorrelation(step);
            averageParameter = streamingRealVectorCalculator.calculateAverage(currentParameter);
            iteration++;
            continueExecuting = util.needToContinue(iteration, minimumIterations, maximumIterations, currentParameter, autocorrelation);
            logIteration(gain, currentParameter, autocorrelation);
        } while (continueExecuting);
        return averageParameter;
    }

    private void initializeFields(ParametricProcess process) {
        parameterSize = process.parameterSize();
        averageParameter = new SafeObjectVector(process.getIndex());
        streamingRealVectorCalculator.initialize(process.getIndex());
        iteration = 0;
    }

    private void logIteration(double gain, ObjectVector parameter, ObjectVector autocorrelation) {
        if (iteration % LOGGER_UPDATE_INTERVAL == 0) {
            LOG.info(String.format("[+] Robbins-Monro iteration %s, gain = %5.4f%n   #      param   autocorr", iteration, gain));
            for (int i = 0; i < parameterSize; i++) {
                LOG.info(String.format("    %2d %+10.2f %+10.2f", i, parameter.getEntry(i), autocorrelation.getEntry(i)));
            }
        }
    }
}
