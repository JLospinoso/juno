package net.lospi.juno.estimation.ml;

import net.lospi.juno.model.ObjectVector;
import net.lospi.juno.model.SafeObjectVector;

import java.util.List;

public class StreamingRealVectorCalculator {
    private ObjectVector autocorrelation;
    private ObjectVector lastVector;
    private ObjectVector sum;
    private int summands;
    private boolean initialized;

    public StreamingRealVectorCalculator() {
        initialized = false;
    }

    public void initialize(List index) {
        autocorrelation = new SafeObjectVector(index);
        lastVector = new SafeObjectVector(index);
        sum = new SafeObjectVector(index);
        summands = 0;
        initialized = true;
    }

    public ObjectVector calculateAutocorrelation(ObjectVector vector) {
        checkInitialization();
        autocorrelation = autocorrelation.add(vector.ebeMultiply(lastVector));
        lastVector = vector;
        return autocorrelation;
    }

    public ObjectVector calculateAverage(ObjectVector vector) {
        checkInitialization();
        summands++;
        sum = sum.add(vector);
        return sum.mapDivide(summands);
    }

    private void checkInitialization() {
        if(!initialized) {
            throw new IllegalStateException("You must call initialize before calculating.");
        }
    }
}
