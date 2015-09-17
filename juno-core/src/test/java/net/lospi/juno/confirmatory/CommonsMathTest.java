

package net.lospi.juno.confirmatory;

        import org.apache.commons.math3.linear.*;
        import org.fest.assertions.data.Offset;
        import org.testng.annotations.Test;

        import static org.fest.assertions.api.Assertions.assertThat;
        import static org.fest.assertions.api.Assertions.offset;

@Test(groups="unit")
public class CommonsMathTest {
    private final double[][] a = {{1d,2d,3d},{4d,5d,6d}};
    private final double[][] b = {{-4d,5d},{-6d,-7d},{8d,9d}};
    private final double[] d = {1d,2d,3d};
    private final double[][] p = {{1d,2d},{3d,4d}};
    private final Offset<Double> tolerance = offset(1e-15d);

    public void matrixMultiplication() {
        RealMatrix rmA = MatrixUtils.createRealMatrix(a);
        RealMatrix rmB = MatrixUtils.createRealMatrix(b);
        RealMatrix rmC = rmA.multiply(rmB);
        /*
            Expected:
               8    18
               2    39
         */
        assertThat(rmC.getEntry(0,0)).isEqualTo(8d, tolerance);
        assertThat(rmC.getEntry(1, 0)).isEqualTo( 2d, tolerance);
        assertThat(rmC.getEntry(0,1)).isEqualTo(18d, tolerance);
        assertThat(rmC.getEntry(1,1)).isEqualTo(39d, tolerance);
        assertThat(rmC.getRowDimension()).isEqualTo(2);
        assertThat(rmC.getColumnDimension()).isEqualTo(2);
    }

    public void inverse() {
        RealMatrix rmP = MatrixUtils.createRealMatrix(p);
        RealMatrix pInverse = new LUDecomposition(rmP).getSolver().getInverse();
        /*
            Expected:
            -2        1
             1.5     -.5
         */
        assertThat(pInverse.getEntry(0,0)).isEqualTo(-2.0d, tolerance);
        assertThat(pInverse.getEntry(1,0)).isEqualTo( 1.5d, tolerance);
        assertThat(pInverse.getEntry(0,1)).isEqualTo( 1.0d, tolerance);
        assertThat(pInverse.getEntry(1,1)).isEqualTo(-0.5d, tolerance);
        assertThat(pInverse.getRowDimension()).isEqualTo(2);
        assertThat(pInverse.getColumnDimension()).isEqualTo(2);
    }

    public void outerProduct() {
        RealVector rmd = new ArrayRealVector( MatrixUtils.createRealVector(d).toArray() );
        RealMatrix op = rmd.outerProduct(rmd);
        /*
            Expected:
            1   2   3
            2   4   6
            3   6   9
         */
        assertThat(op.getEntry(0,0)).isEqualTo(1d, tolerance);
        assertThat(op.getEntry(1,0)).isEqualTo(2d, tolerance);
        assertThat(op.getEntry(2,0)).isEqualTo(3d, tolerance);
        assertThat(op.getEntry(0,1)).isEqualTo(2d, tolerance);
        assertThat(op.getEntry(1,1)).isEqualTo(4d, tolerance);
        assertThat(op.getEntry(2,1)).isEqualTo(6d, tolerance);
        assertThat(op.getEntry(0,2)).isEqualTo(3d, tolerance);
        assertThat(op.getEntry(1,2)).isEqualTo(6d, tolerance);
        assertThat(op.getEntry(2,2)).isEqualTo(9d, tolerance);
        assertThat(op.getRowDimension()).isEqualTo(3);
        assertThat(op.getColumnDimension()).isEqualTo(3);
    }

    public void matrixByVector() {
        RealVector rmd = new ArrayRealVector( MatrixUtils.createRealVector(d).toArray() );
        RealMatrix rmB = MatrixUtils.createRealMatrix(b);
        RealVector rmZ = rmB.preMultiply(rmd);
        /*
            Expected:
            8   18
         */
        assertThat(rmZ.getEntry(0)).isEqualTo( 8d, tolerance);
        assertThat(rmZ.getEntry(1)).isEqualTo(18d, tolerance);
        assertThat(rmZ.getDimension()).isEqualTo(2);
    }
}