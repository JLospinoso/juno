package net.lospi.juno.model;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.LUDecomposition;

import java.util.Collections;
import java.util.List;

public class SafeObjectMatrix implements ObjectMatrix {
    private final int nRow, nCol;
    private final double[][] values;
    private final List rowIndex, colIndex;

    //TODO: Make a builder class
    public SafeObjectMatrix(List rowIndex, List colIndex) {
        this(new double[rowIndex.size()][colIndex.size()], rowIndex, colIndex);
    }

    public SafeObjectMatrix(double[][] values, List rowIndex, List colIndex) {
        this.nRow = values.length;
        if(nRow <= 0) {
            throw new DimensionMismatchException(String.format("Values had %d rows and must have at least one.", nRow));
        }
        this.nCol = values[0].length;
        if(nCol <= 0) {
            throw new DimensionMismatchException(String.format("Values had %d cols and must have at least one.", nCol));
        }
        for(int row = 0; row < nRow; row++) {
            if(values[row].length != nCol) {
                throw new DimensionMismatchException(String.format("Values had jagged column %d with %d elements, should have %d",
                        row, values[row].length, nCol));
            }
        }
        this.rowIndex = Collections.unmodifiableList(rowIndex);
        if(rowIndex.size() != nRow) {
            throw new DimensionMismatchException(String.format("Values had %d rows and rowIndex had %d elements.",
                    nRow, rowIndex.size()));
        }
        this.colIndex = Collections.unmodifiableList(colIndex);
        if(colIndex.size() != nCol) {
            throw new DimensionMismatchException(String.format("Values had %d cols and colIndex had %d elements.",
                    nCol, colIndex.size()));
        }
        this.values = values;
    }

    @Override
    public ObjectMatrix add(ObjectMatrix m) throws DimensionMismatchException {
        checkMatrix(m);
        double[][] newValues = new double[nRow][nCol];
        for(int row=0; row<nRow; row++) {
            for(int col=0; col<nCol; col++) {
                newValues[row][col] = values[row][col] + m.getEntry(row, col);
            }
        }
        return new SafeObjectMatrix(newValues, rowIndex, colIndex);
    }

    @Override
    public ObjectMatrix subtract(ObjectMatrix m) throws DimensionMismatchException {
        checkMatrix(m);
        double[][] newValues = new double[nRow][nCol];
        for(int row=0; row<nRow; row++) {
            for(int col=0; col<nCol; col++) {
                newValues[row][col] = values[row][col] - m.getEntry(row, col);
            }
        }
        return new SafeObjectMatrix(newValues, rowIndex, colIndex);
    }

    @Override
    public SafeObjectMatrix scalarMultiply(double d) {
        double[][] newValues = new double[nRow][nCol];
        for(int row=0; row<nRow; row++) {
            for(int col=0; col<nCol; col++) {
                newValues[row][col] = values[row][col] * d;
            }
        }
        return new SafeObjectMatrix(newValues, rowIndex, colIndex);
    }

    @Override
    public ObjectVector preMultiply(ObjectVector x) throws DimensionMismatchException {
        checkVectorCompatibilityWithRows(x);
        double[] result = new double[nCol];
        for(int row = 0; row < nRow; row++) {
            for(int col = 0; col < nCol; col++) {
                result[col] += x.getEntry(row) * getEntry(row, col);
            }
        }
        return new SafeObjectVector(result, colIndex);
    }

    @Override
    public ObjectVector getRowVector(int row) throws OutOfRangeException {
        checkRow(row);
        return new SafeObjectVector(values[row], colIndex);
    }

    @Override
    public ObjectVector getColumnVector(int column) throws OutOfRangeException {
        checkCol(column);
        double[] columnVector = new double[nRow];
        for(int row=0; row<nRow; row++) {
            columnVector[row] = getEntry(row, column);
        }
        return new SafeObjectVector(columnVector, rowIndex);
    }

    @Override
    public double getEntry(int row, int column) throws OutOfRangeException {
        checkRow(row);
        checkCol(column);
        return values[row][column];
    }

    @Override
    public int getRowDimension() {
        return nRow;
    }

    @Override
    public int getColumnDimension() {
        return nCol;
    }

    @Override
    public ObjectMatrix getInverse() {
        if(nRow != nCol) {
            throw new DimensionMismatchException(String.format("Cannot find inverse of %d x %d matrix", nRow, nCol));
        }
        DoubleMatrix2D asMatrix = new DenseDoubleMatrix2D(values);
        LUDecomposition luDecomposition = new LUDecomposition(asMatrix);
        DoubleMatrix2D solution = luDecomposition.solve(identity(nRow));
        double[][] values = new double[nRow][nRow];
        for(int row=0; row<nRow; row++) {
            for(int col=0; col<nCol; col++) {
                values[row][col] = solution.get(row, col);
            }
        }
        return new SafeObjectMatrix(values, rowIndex, rowIndex);
    }

    @Override
    public List getRowIndex() {
        return rowIndex;
    }

    @Override
    public List getColIndex() {
        return colIndex;
    }


    private DoubleMatrix2D identity(int nRow) {
        DoubleMatrix2D result = new DenseDoubleMatrix2D(nRow, nRow);
        for(int i=0; i<nRow; i++) {
            result.set(i, i, 1);
        }
        return result;
    }

    private void checkMatrix(ObjectMatrix m) {
        if(m.getRowDimension() != nRow) {
            throw new DimensionMismatchException(String.format("Column dimensions must match: %d != %d",
                    getRowDimension(), nRow));
        }
        if(m.getColumnDimension() != nCol) {
            throw new DimensionMismatchException(String.format("Column dimensions must match: %d != %d",
                    getColumnDimension(), nCol));
        }
        if(!getRowIndex().equals(m.getRowIndex())) {
            throw new DimensionMismatchException(String.format("Row indices must match: %s != %s",
                    getRowIndex(), m.getRowIndex()));
        }
        if(!getColIndex().equals(m.getColIndex())) {
            throw new DimensionMismatchException(String.format("Column indices must match: %s != %s",
                    getColIndex(), m.getColIndex()));
        }
    }

    private void checkVectorCompatibilityWithRows(ObjectVector x) {
        if(x.getDimension() != nRow) {
            throw new DimensionMismatchException(
                    String.format("ObjectVector must have size equal to the number of rows in the matrix (%d).", nRow));
        }
        if(!x.getIndex().equals(rowIndex)) {
            throw new DimensionMismatchException(
                    String.format("Row list must match vector list %s != %s.", x.getIndex(), colIndex));
        }
    }

    private void checkCol(int column) {
        if(column < 0) {
            throw new OutOfRangeException(String.format("Index must be greater than or equal to 0 (was %d).", column));
        }
        if(column >= nCol) {
            throw new OutOfRangeException(String.format("Index less than or equal to number of columns %d (was %d).", nCol, column));
        }
    }

    private void checkRow(int row) {
        if(row < 0) {
            throw new OutOfRangeException(String.format("Index must be greater than or equal to 0 (was %d).", row));
        }
        if(row >= nRow) {
            throw new OutOfRangeException(String.format("Index less than or equal to number of rows %d (was %d).", nRow, row));
        }
    }
}
