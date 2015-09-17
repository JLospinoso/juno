package net.lospi.juno.model;

import java.util.List;
import java.util.function.Function;

public interface ObjectVector {
    int getDimension();
    double getEntry(int index) throws OutOfRangeException;
    ObjectVector getSubVector(int index, int n) throws NotPositiveException, OutOfRangeException;
    ObjectVector ebeMultiply(ObjectVector v) throws DimensionMismatchException, IndexMismatchException;
    ObjectVector map(Function<Double, Double> x);
    ObjectVector mapSubtract(double x);
    ObjectMatrix outerProduct(ObjectVector candidateScore) throws IndexMismatchException;
    ObjectVector subtract(ObjectVector expectedStatistics) throws IndexMismatchException;
    ObjectVector mapMultiply(double v);
    ObjectVector add(ObjectVector step) throws IndexMismatchException;
    ObjectVector mapDivide(double x);
    List getIndex();
    double[] asArray(List alterSelectionEffects);
}
