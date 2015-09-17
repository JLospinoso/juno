package net.lospi.juno.estimation.elements;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.model.Effect;
import net.lospi.juno.model.ObjectVector;
import net.lospi.juno.model.SafeObjectVector;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class ConvergenceRatioTest {
    private ObjectVector tRatios;
    private List<Effect> effects;
    private Effect effect1, effect2;
    @BeforeMethod
    public void setUp() {
        effect1 = mock(Effect.class);
        effect2 = mock(Effect.class);
        effects = ImmutableList.of(effect1, effect2);
        tRatios = new SafeObjectVector(new double[] { 1D, 2D }, effects);
    }

    public void getEffects() {
        ConvergenceRatio underStudy = new ConvergenceRatio(tRatios, effects);
        List result = underStudy.getEffects();
        assertThat(result).isEqualTo(effects);
    }

    public void getTRatios() {
        ConvergenceRatio underStudy = new ConvergenceRatio(tRatios, effects);
        ObjectVector result = underStudy.getTRatios();
        assertThat(result).isSameAs(tRatios);
    }

    public void canToString() {
        ConvergenceRatio underStudy = new ConvergenceRatio(tRatios, effects);
        String result = underStudy.toString();
        assertThat(result).isNotNull();
    }
}