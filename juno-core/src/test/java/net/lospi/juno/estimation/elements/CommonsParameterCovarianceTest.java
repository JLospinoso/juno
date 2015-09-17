package net.lospi.juno.estimation.elements;

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
public class CommonsParameterCovarianceTest {
    private ObjectMatrix covariance;
    private List<Effect> effects;

    @BeforeMethod
    public void setUp() {
        effects = ImmutableList.of(mock(Effect.class), mock(Effect.class));
        covariance = new SafeObjectMatrix(new double[][] {
                new double[] { 1, 2},
                new double[] { 3, 4}
        }, effects, effects);
    }

    public void getEffects() {
        CommonsParameterCovariance underStudy = new CommonsParameterCovariance(covariance, effects);
        List<Effect> result = underStudy.getEffects();
        assertThat(result).isEqualTo(effects);
    }

    public void canToString() {
        CommonsParameterCovariance underStudy = new CommonsParameterCovariance(covariance, effects);
        String result = underStudy.toString();
        assertThat(result).isNotNull();
    }

    public void getCovariance() {
        CommonsParameterCovariance underStudy = new CommonsParameterCovariance(covariance, effects);
        ObjectMatrix result = underStudy.getCovariance();
        assertThat(result).isEqualTo(covariance);
    }
}
