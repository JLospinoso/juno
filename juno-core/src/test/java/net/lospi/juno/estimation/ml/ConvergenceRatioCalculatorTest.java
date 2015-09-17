package net.lospi.juno.estimation.ml;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.estimation.elements.ConvergenceRatio;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.elements.ParameterCovariance;
import net.lospi.juno.model.*;
import org.fest.assertions.data.Offset;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.offset;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups = "unit")
public class ConvergenceRatioCalculatorTest {
    private static final Double DOUBLE_ASSERTION_TOLERANCE = 1e-15d;

    private List<LikelihoodDerivatives> simulatedDerivatives;
    private ParameterCovariance covariance;
    private List<Effect> effects;
    private LikelihoodDerivatives derivative1, derivative2;
    private ObjectVector score1, score2;
    private Effect effect1, effect2;
    private ObjectMatrix covarianceMatrix;
    private Offset<Double> tolerance = offset(DOUBLE_ASSERTION_TOLERANCE);

    @BeforeMethod
    public void setUp() throws Exception {
        effect1 = mock(Effect.class);
        effect2 = mock(Effect.class);
        covariance = mock(ParameterCovariance.class);
        derivative1 = mock(LikelihoodDerivatives.class);
        derivative2 = mock(LikelihoodDerivatives.class);
        simulatedDerivatives = ImmutableList.of(derivative1, derivative2);
        effects = ImmutableList.of(effect1, effect2);
        score1 = new SafeObjectVector(new double[] { 1D, 2D }, effects);
        score2 = new SafeObjectVector(new double[] { 3D, 4D }, effects);
        covarianceMatrix = new SafeObjectMatrix(new double[][] {
                        new double[] { 10D, 0D },
                        new double[] { 0D, 10D }
        }, effects, effects);

        when(derivative1.getScore()).thenReturn(score1);
        when(derivative2.getScore()).thenReturn(score2);
        when(covariance.getEffects()).thenReturn(effects);
        when(covariance.getCovariance()).thenReturn(covarianceMatrix);
    }

    public void effectsCorrect() throws Exception {
        ConvergenceRatioCalculator calculator = new ConvergenceRatioCalculator();
        ConvergenceRatio result = calculator.calculate(simulatedDerivatives, covariance);
        assertThat(result.getEffects()).isEqualTo(effects);
    }

    public void tRatiosCorrect() throws Exception {
        ConvergenceRatioCalculator calculator = new ConvergenceRatioCalculator();
        ConvergenceRatio result = calculator.calculate(simulatedDerivatives, covariance);
        assertThat(result.getTRatios().getEntry(0)).isEqualTo(20D, tolerance);
        assertThat(result.getTRatios().getEntry(1)).isEqualTo(30D, tolerance);
    }
}