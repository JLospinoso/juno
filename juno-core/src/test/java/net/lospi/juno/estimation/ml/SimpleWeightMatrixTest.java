package net.lospi.juno.estimation.ml;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.model.Effect;
import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.SafeObjectMatrix;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class SimpleWeightMatrixTest {
    private ObjectMatrix weights;
    private List<Effect> effects;
    private Effect effect1, effect2;

    @BeforeMethod
    public void setUp() {
        effect1 = mock(Effect.class);
        effect2 = mock(Effect.class);
        effects = ImmutableList.of(effect1, effect2);
        weights = new SafeObjectMatrix(new double[][] {
                new double[] { 1d, 2d },
                new double[] { 2d, 4d },
        }, effects, effects);
    }

    public void getWeights() {
        SimpleWeightMatrix underStudy = new SimpleWeightMatrix(weights, effects);
        ObjectMatrix result = underStudy.getWeights();
        assertThat(result).isEqualTo(weights);
    }

    public void getEffects() {
        SimpleWeightMatrix underStudy = new SimpleWeightMatrix(weights, effects);
        List<Effect> result = underStudy.getEffects();
        assertThat(result).isEqualTo(effects);
    }

    public void canToString() {
        SimpleWeightMatrix underStudy = new SimpleWeightMatrix(weights, effects);
        String result = underStudy.toString();
        assertThat(result).isNotNull();
    }
}
