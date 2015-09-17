package net.lospi.juno.estimation.ml;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.model.Effect;
import net.lospi.juno.model.SafeObjectMatrix;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups = "unit")
public class PhaseOneResultsBuilderTest {
    private WeightMatrix weightMatrix;
    private String failureMessage;
    private List<Effect> effects;

    @BeforeMethod
    public void setUp() throws Exception {
        effects = ImmutableList.of(mock(Effect.class), mock(Effect.class));
        weightMatrix = mock(WeightMatrix.class);

        when(weightMatrix.getEffects()).thenReturn(effects);
        failureMessage = "fail";
    }

    public void success() throws Exception {
        when(weightMatrix.getWeights()).thenReturn(new SafeObjectMatrix(new double[][]{
                new double[] { 1D, 0D },
                new double[] { 0D, 2D }
        }, effects, effects));
        PhaseOneResultsBuilder builder = new PhaseOneResultsBuilder();
        PhaseOneResults result = builder.with().weightMatrix(weightMatrix).build();
        assertThat(result.getWeightMatrix()).isEqualTo(weightMatrix);
        assertThat(result.isSuccessful()).isTrue();
        assertThat(result.getStatus()).isEqualTo("Completed with positive weight diagonal");
    }

    public void badWeights() throws Exception {
        when(weightMatrix.getWeights()).thenReturn(new SafeObjectMatrix(new double[][]{
                new double[] { -1D,  1D },
                new double[] {  1D, -2D }
        }, effects, effects));

        PhaseOneResultsBuilder builder = new PhaseOneResultsBuilder();
        PhaseOneResults result = builder.with().weightMatrix(weightMatrix).build();
        assertThat(result.getWeightMatrix()).isEqualTo(weightMatrix);
        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getStatus()).isNotNull();
    }

    public void failure() throws Exception {
        PhaseOneResultsBuilder builder = new PhaseOneResultsBuilder();
        PhaseOneResults result = builder.with().failure(failureMessage).build();
        assertThat(result.getWeightMatrix()).isNull();
        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getStatus()).isEqualTo(failureMessage);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void forgetToSet() throws Exception {
        PhaseOneResultsBuilder builder = new PhaseOneResultsBuilder();
        builder.with().build();
    }
}