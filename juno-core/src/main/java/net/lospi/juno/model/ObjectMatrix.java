package net.lospi.juno.model;

import java.util.List;

public interface ObjectMatrix {
    ObjectMatrix add(ObjectMatrix m) throws DimensionMismatchException;
    ObjectMatrix subtract(ObjectMatrix m) throws DimensionMismatchException;
    ObjectMatrix scalarMultiply(double d);
    ObjectVector preMultiply(ObjectVector x) throws DimensionMismatchException;
    ObjectVector getRowVector(int row) throws OutOfRangeException;
    ObjectVector getColumnVector(int column) throws OutOfRangeException;
    double getEntry(int row, int column) throws OutOfRangeException;
    int getRowDimension();
    int getColumnDimension();
    ObjectMatrix getInverse();
    List getRowIndex();
    List getColIndex();
}
