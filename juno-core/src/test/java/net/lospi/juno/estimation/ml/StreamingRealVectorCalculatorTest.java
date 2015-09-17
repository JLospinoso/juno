package net.lospi.juno.estimation.ml;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.model.ObjectVector;
import net.lospi.juno.model.SafeObjectVector;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class StreamingRealVectorCalculatorTest {
    private int size;
    private SafeObjectVector vector1, vector2, vector3;
    private List index;

    @BeforeMethod
    public void setUp() {
        index = ImmutableList.of("Entry1", "Entry2");
        size = 2;
        vector1 = new SafeObjectVector(new double[] { 0, 1 }, index);
        vector2 = new SafeObjectVector(new double[] { 2, 3 }, index);
        vector3 = new SafeObjectVector(new double[] { 4, 5 }, index);
    }

    public void resetAverage() {
        StreamingRealVectorCalculator underStudy = new StreamingRealVectorCalculator();
        underStudy.initialize(index);
        underStudy.calculateAverage(vector3);
        underStudy.calculateAverage(vector3);
        underStudy.calculateAverage(vector3);
        underStudy.initialize(index);
        underStudy.calculateAverage(vector1);
        underStudy.calculateAverage(vector2);
        ObjectVector result = underStudy.calculateAverage(vector3);
        assertThat(result.getEntry(0)).isEqualTo(2);
        assertThat(result.getEntry(1)).isEqualTo(3);
    }

    public void resetAutocorrelation() {
        StreamingRealVectorCalculator underStudy = new StreamingRealVectorCalculator();
        underStudy.initialize(index);
        underStudy.calculateAutocorrelation(vector1);
        underStudy.calculateAutocorrelation(vector1);
        underStudy.calculateAutocorrelation(vector2);
        underStudy.calculateAutocorrelation(vector1);
        underStudy.initialize(index);
        underStudy.calculateAutocorrelation(vector1);
        underStudy.calculateAutocorrelation(vector2);
        ObjectVector result = underStudy.calculateAutocorrelation(vector3);
        assertThat(result.getEntry(0)).isEqualTo(8);
        assertThat(result.getEntry(1)).isEqualTo(18);
    }

    public void calculateAverage() {
        StreamingRealVectorCalculator underStudy = new StreamingRealVectorCalculator();
        underStudy.initialize(index);
        underStudy.calculateAverage(vector1);
        underStudy.calculateAverage(vector2);
        ObjectVector result = underStudy.calculateAverage(vector3);
        assertThat(result.getEntry(0)).isEqualTo(2);
        assertThat(result.getEntry(1)).isEqualTo(3);
    }

    public void calculateAutocorrelation() {
        StreamingRealVectorCalculator underStudy = new StreamingRealVectorCalculator();
        underStudy.initialize(index);
        underStudy.calculateAutocorrelation(vector1);
        underStudy.calculateAutocorrelation(vector2);
        ObjectVector result = underStudy.calculateAutocorrelation(vector3);
        assertThat(result.getEntry(0)).isEqualTo(8);
        assertThat(result.getEntry(1)).isEqualTo(18);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void noInitializationAutocorrelation() {
        StreamingRealVectorCalculator underStudy = new StreamingRealVectorCalculator();
        underStudy.calculateAutocorrelation(vector1);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void noInitializationAverage() {
        StreamingRealVectorCalculator underStudy = new StreamingRealVectorCalculator();
        underStudy.calculateAverage(vector1);
    }
}
