package net.lospi.juno.estimation.sim;

import net.lospi.juno.model.ObjectVector;

import java.util.List;

public interface ParametricProcess {
    ObjectVector sample();
    ObjectVector addToAndSetParameters(ObjectVector step);
    List getIndex();
    int parameterSize();
    void setParameters(ObjectVector solution);
}
