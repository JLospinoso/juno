package net.lospi.juno.estimation.ml;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.estimation.elements.ConvergenceRatio;
import net.lospi.juno.estimation.elements.ParameterCovariance;
import net.lospi.juno.model.Effect;
import net.lospi.juno.model.SafeObjectMatrix;
import net.lospi.juno.model.SafeObjectVector;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups = "unit")
public class PhaseThreeResultsBuilderTest {

    private ParameterCovariance covariance;
    private String failureMessage;
    private ConvergenceRatio convergenceRatio;
    private List<Effect> effects;

    @BeforeMethod
    public void setUp() throws Exception {
        effects = ImmutableList.of(mock(Effect.class), mock(Effect.class));
        convergenceRatio = mock(ConvergenceRatio.class);
        covariance = mock(ParameterCovariance.class);

        when(convergenceRatio.getEffects()).thenReturn(effects);
        when(covariance.getEffects()).thenReturn(effects);
        when(convergenceRatio.getTRatios()).thenReturn(new SafeObjectVector(new double[] { .01D, .02D }, effects));
        when(covariance.getCovariance()).thenReturn(new SafeObjectMatrix(new double[][]{
                new double[] { 3D, 0D },
                new double[] { 0D, 4D }
        }, effects, effects));

        failureMessage = "fail";
    }

    public void success() throws Exception {
        PhaseThreeResultsBuilder builder = new PhaseThreeResultsBuilder();
        PhaseThreeResults result = builder.with()
                .covariance(covariance)
                .convergenceRatio(convergenceRatio)
                .build();
        assertThat(result.getParameterCovariance()).isEqualTo(covariance);
        assertThat(result.getConvergenceRatio()).isEqualTo(convergenceRatio);
        assertThat(result.isSuccessful()).isTrue();
        assertThat(result.getStatus()).isEqualTo("Completed with positive covariance diagonal and all convergence ratios < 0.150");
    }

    public void failWithBadConvergence() throws Exception {
        when(convergenceRatio.getTRatios()).thenReturn(new SafeObjectVector(new double[] { .01D, .3D }, effects));
        PhaseThreeResultsBuilder builder = new PhaseThreeResultsBuilder();
        PhaseThreeResults result = builder.with()
                .covariance(covariance)
                .convergenceRatio(convergenceRatio)
                .build();
        assertThat(result.getParameterCovariance()).isEqualTo(covariance);
        assertThat(result.getConvergenceRatio()).isEqualTo(convergenceRatio);
        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getStatus()).isNotNull();
    }

    public void failWithBadCovariance() throws Exception {
        when(covariance.getCovariance()).thenReturn(new SafeObjectMatrix(new double[][]{
                new double[] { 3D, 0D },
                new double[] { 0D,-4D }
        }, effects, effects));
        PhaseThreeResultsBuilder builder = new PhaseThreeResultsBuilder();
        PhaseThreeResults result = builder.with()
                .covariance(covariance)
                .convergenceRatio(convergenceRatio)
                .build();
        assertThat(result.getParameterCovariance()).isEqualTo(covariance);
        assertThat(result.getConvergenceRatio()).isEqualTo(convergenceRatio);
        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getStatus()).isNotNull();
    }

    public void failure() throws Exception {
        PhaseThreeResultsBuilder builder = new PhaseThreeResultsBuilder();
        PhaseThreeResults result = builder.with().failure(failureMessage).build();
        assertThat(result.getParameterCovariance()).isNull();
        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getStatus()).isEqualTo(failureMessage);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void forgotToSetTRatio() throws Exception {
        PhaseThreeResultsBuilder builder = new PhaseThreeResultsBuilder();
        builder.with().covariance(covariance).build();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void forgotToSetCovariance() throws Exception {
        PhaseThreeResultsBuilder builder = new PhaseThreeResultsBuilder();
        builder.with().convergenceRatio(convergenceRatio).build();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void forgetToSetAnything() throws Exception {
        PhaseThreeResultsBuilder builder = new PhaseThreeResultsBuilder();
        builder.with().build();
    }
}