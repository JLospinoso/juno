package net.lospi.juno.stat;

import net.lospi.juno.model.Effect;
import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.ObjectVector;

import java.util.List;

public interface Statistics<T extends Effect> {
    List<T> getEffects();
    List<String> getOutcomes();
    ObjectVector getByOutcome(String outcome);
    ObjectMatrix getRowsAsOutcomes();
    int indexOfOutcome(String outcome);
    int indexOfEffect(T effect);
    ObjectVector getByEffect(T effect);
}
