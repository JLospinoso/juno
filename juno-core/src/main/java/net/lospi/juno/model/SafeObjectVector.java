package net.lospi.juno.model;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class SafeObjectVector implements ObjectVector {
    private final int size;
    private final double[] values;
    private final List index;

    public SafeObjectVector(List index) {
        this.size = index.size();
        this.index = index;
        this.values = new double[size];
    }

    public SafeObjectVector(double[] values, List index) {
        if(values.length != index.size()) {
            throw new DimensionMismatchException("Values and index must have the same size.");
        }
        this.size = index.size();
        this.index = index;
        this.values = values;
    }

    @Override
    public int getDimension() {
        return size;
    }

    @Override
    public double getEntry(int index) throws OutOfRangeException {
        checkIndex(index);
        return values[index];
    }

    @Override
    public ObjectVector getSubVector(int startingIndex, int elements) throws NotPositiveException, OutOfRangeException {
        checkIndex(startingIndex);
        int indexOfLastElement = startingIndex + elements - 1;
        if(indexOfLastElement >= size) {
            throw new DimensionMismatchException(String.format("Vector size %d must be less than implied index of last element (%d)",
                    size, indexOfLastElement));
        }
        double[] newValues = new double[elements];
        for(int i=0; i<elements; i++) {
            newValues[i] = values[i + startingIndex];
        }
        List newIndex = this.index.subList(startingIndex, indexOfLastElement + 1);
        return new SafeObjectVector(newValues, newIndex);
    }

    @Override
    public ObjectVector ebeMultiply(ObjectVector v) throws DimensionMismatchException {
        double[] newValues = new double[size];
        for(int i=0; i<size; i++) {
            newValues[i] = v.getEntry(i) * values[i];
        }
        return new SafeObjectVector(newValues, index);
    }

    @Override
    public SafeObjectVector map(Function<Double, Double> f) {
        double[] newValues = new double[size];
        for(int i=0; i<size; i++) {
            newValues[i] = f.apply(values[i]);
        }
        return new SafeObjectVector(newValues, index);
    }

    @Override
    public ObjectVector mapSubtract(double x) {
        double[] newValues = new double[size];
        for(int i=0; i<size; i++) {
            newValues[i] = values[i] - x;
        }
        return new SafeObjectVector(newValues, index);
    }

    @Override
    public SafeObjectMatrix outerProduct(ObjectVector x) {
        checkVector(x);
        double[][] result = new double[size][size];
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                result[i][j] = values[i] * x.getEntry(j);
            }
        }
        return new SafeObjectMatrix(result, index, index);
    }

    @Override
    public ObjectVector subtract(ObjectVector x) {
        checkVector(x);
        double[] newValues = new double[size];
        for(int i=0; i<size; i++) {
            newValues[i] = values[i] - x.getEntry(i);
        }
        return new SafeObjectVector(newValues, index);
    }

    @Override
    public ObjectVector mapMultiply(double v) {
        double[] newValues = new double[size];
        for(int i=0; i<size; i++) {
            newValues[i] = values[i] * v;
        }
        return new SafeObjectVector(newValues, index);
    }

    @Override
    public ObjectVector add(ObjectVector x) {
        checkVector(x);
        double[] newValues = new double[size];
        for(int i=0; i<size; i++) {
            newValues[i] = values[i] + x.getEntry(i);
        }
        return new SafeObjectVector(newValues, index);
    }

    @Override
    public SafeObjectVector mapDivide(double x) {
        double[] newValues = new double[size];
        for(int i=0; i<size; i++) {
            newValues[i] = values[i] / x;
        }
        return new SafeObjectVector(newValues, index);
    }

    @Override
    public List getIndex() {
        return index;
    }

    @Override
    public double[] asArray(List index) throws IndexMismatchException {
        checkIndex(index);
        return values;
    }

    private void checkIndex(int index) {
        if(index < 0) {
            throw new OutOfRangeException("Index cannot be negative.");
        }
        if(index >= size) {
            throw new OutOfRangeException(String.format("Index %d cannot be greater than or equal to size %d", index, size));
        }
    }

    private void checkVector(ObjectVector x) {
        if(x.getDimension() != getDimension()) {
            throw new DimensionMismatchException(String.format("Vector sizes must be equal: %d != %d\n", getDimension(), x.getDimension()));
        }
        checkIndex(x.getIndex());
    }

    private void checkIndex(List index) {
        if(! this.index.equals(index)) {
            throw new DimensionMismatchException(String.format("Indices must be equal: %s != %s\n", getIndex(), index));
        }
    }
}
