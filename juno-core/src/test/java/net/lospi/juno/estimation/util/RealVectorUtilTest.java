package net.lospi.juno.estimation.util;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.model.SafeObjectVector;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@Test(groups="unit")
public class RealVectorUtilTest {
    private List index;

    @BeforeMethod
    public void setUp() {
        index = ImmutableList.of(1, 2, 3, 4, 5, 6);
    }

    public void anyPositiveTrue() {
        RealVectorUtil underStudy = new RealVectorUtil();
        boolean result = underStudy.anyPositive(new SafeObjectVector(new double[]{ -2, -1, -1e-15, 1, -1e-15, -1 }, index));
        assertThat(result).isTrue();
    }

    public void anyWithMagnitudeGreaterThanTrueNegative() {
        RealVectorUtil underStudy = new RealVectorUtil();
        boolean result = underStudy.anyWithMagnitudeGreaterThan(new SafeObjectVector(new double[]{ -2, -1, 0, -50, 0, -1 }, index), 49);
        assertThat(result).isTrue();
    }

    public void anyWithMagnitudeGreaterThanTruePositive() {
        RealVectorUtil underStudy = new RealVectorUtil();
        boolean result = underStudy.anyWithMagnitudeGreaterThan(new SafeObjectVector(new double[]{ -2, -1, 0, 50, 0, -1 }, index), 49);
        assertThat(result).isTrue();
    }

    public void anyWithMagnitudeGreaterThanFalse() {
        RealVectorUtil underStudy = new RealVectorUtil();
        boolean result = underStudy.anyWithMagnitudeGreaterThan(new SafeObjectVector(new double[]{ -2, -1, 0, 48, 0, -1 }, index), 49);
        assertThat(result).isFalse();
    }

    public void anyPositiveFalse() {
        RealVectorUtil underStudy = new RealVectorUtil();
        boolean result = underStudy.anyPositive(new SafeObjectVector(new double[]{ -2, -1, -1e-15, -1, -1e-15, -1 }, index));
        assertThat(result).isFalse();
    }
}
