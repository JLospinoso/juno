package net.lospi.juno.estimation.util;

import net.lospi.juno.model.ObjectVector;

public class RealVectorUtil {
    public RealVectorUtil() {

    }

    public boolean anyWithMagnitudeGreaterThan(ObjectVector parameters, double maximumMagnitude) {
        for(int i=0; i<parameters.getDimension(); i++) {
            if(Math.abs(parameters.getEntry(i)) > maximumMagnitude) {
                return true;
            }
        }
        return false;
    }

    public boolean anyPositive(ObjectVector autocorrelation) {
        for(int i=0; i<autocorrelation.getDimension(); i++) {
            if(autocorrelation.getEntry(i) > 0D) {
                return true;
            }
        }
        return false;
    }
}
