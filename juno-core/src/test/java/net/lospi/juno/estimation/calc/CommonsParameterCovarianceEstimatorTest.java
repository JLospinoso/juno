package net.lospi.juno.estimation.calc;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.elements.ParameterCovariance;
import net.lospi.juno.model.Effect;
import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.SafeObjectMatrix;
import org.fest.assertions.data.Offset;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.offset;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class CommonsParameterCovarianceEstimatorTest {
    private List<LikelihoodDerivatives> simulatedDerivatives;
    private LikelihoodDerivatives derivatives1, derivatives2;
    private ObjectMatrix info1, info2;
    private List effects;
    private Offset<Double> tolerance = offset(1e-15);

    @BeforeMethod
    public void setUp() {
        derivatives1 = mock(LikelihoodDerivatives.class);
        derivatives2 = mock(LikelihoodDerivatives.class);
        effects = ImmutableList.of(mock(Effect.class),mock(Effect.class));
        info1 = new SafeObjectMatrix(new double[][] {
                { 2D, 1D},
                { 1D, 2D}
        }, effects, effects);
        info2 = new SafeObjectMatrix(new double[][] {
                { 3D, 1D},
                { 1D, 3D}
        }, effects, effects);

                when(derivatives1.getInformation()).thenReturn(info1);
        when(derivatives2.getInformation()).thenReturn(info2);
        when(derivatives1.getEffects()).thenReturn(effects);
        when(derivatives2.getEffects()).thenReturn(effects);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void calculateWithEmptyDerivativesList() {
        simulatedDerivatives = ImmutableList.of();
        CommonsParameterCovarianceEstimator underStudy = new CommonsParameterCovarianceEstimator();
        underStudy.calculate(simulatedDerivatives);
    }

    public void calculate() {
        simulatedDerivatives = ImmutableList.of(derivatives1, derivatives2);
        CommonsParameterCovarianceEstimator underStudy = new CommonsParameterCovarianceEstimator();
        ParameterCovariance result = underStudy.calculate(simulatedDerivatives);
        ObjectMatrix resultMatrix = result.getCovariance();
        assertThat(resultMatrix.getEntry(0,0)).isEqualTo( 0.47619047619047616, tolerance);
        assertThat(resultMatrix.getEntry(1,0)).isEqualTo(-0.19047619047619047, tolerance);
        assertThat(resultMatrix.getEntry(0,1)).isEqualTo(-0.19047619047619047, tolerance);
        assertThat(resultMatrix.getEntry(1,1)).isEqualTo( 0.47619047619047616, tolerance);
    }
}
