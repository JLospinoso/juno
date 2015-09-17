package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.sim.ParametricProcess;
import net.lospi.juno.estimation.util.RealVectorUtil;
import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.ObjectVector;

public class SerialRobbinsMonroUtil {
    protected static final double MAXIMUM_PARAMETER_MAGNITUDE = 1e4;
    private final RealVectorUtil realVectorUtil;

    public SerialRobbinsMonroUtil(RealVectorUtil realVectorUtil) {
        this.realVectorUtil = realVectorUtil;
    }

    public ObjectVector sampleNextStep(ParametricProcess simulator, ObjectMatrix weight, ObjectVector root, double gain) {
        ObjectVector sample = simulator.sample();
        ObjectVector diff = sample.subtract(root);
        ObjectVector step =  weight.preMultiply(diff);
        return  step.mapMultiply(-1D * gain);
    }

    public boolean needToContinue(int iteration, int minimumIterations, int maximumIterations, ObjectVector parameters, ObjectVector autocorrelation) {
        if(!parametersAreHealthy(parameters)) {
            return false;
        }
        if(iteration > maximumIterations) {
            return false;
        }
        if(iteration < minimumIterations) {
            return true;
        }
        return realVectorUtil.anyPositive(autocorrelation);
    }

    public boolean parametersAreHealthy(ObjectVector parameter) {
        return ! realVectorUtil.anyWithMagnitudeGreaterThan(parameter, MAXIMUM_PARAMETER_MAGNITUDE);
    }
}
