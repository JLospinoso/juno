package net.lospi.juno.estimation.calc;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.model.*;
import org.fest.assertions.data.Offset;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.offset;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//TODO: This is a regression test, haven't done the math manually
@Test(groups="unit")
public class ObservedLikelihoodDerivativesCalculatorTest {
    private List<LikelihoodDerivatives> completeDataDerivatives;
    private List<AlterSelectionEffect> effects;
    private LikelihoodDerivatives derivatives1, derivatives2;
    private AlterSelectionEffect effect1, effect2;
    private double logLikelihood1, logLikelihood2;
    private ObjectVector score1, score2;
    private ObjectMatrix info1, info2;
    private Offset<Double> tolerance = offset(1e-15);

    @BeforeMethod
    public void setUp() {
        effect1 = mock(AlterSelectionEffect.class);
        effect2 = mock(AlterSelectionEffect.class);
        derivatives1 = mock(LikelihoodDerivatives.class);
        derivatives2 = mock(LikelihoodDerivatives.class);
        completeDataDerivatives = ImmutableList.of(derivatives1, derivatives2);
        effects = ImmutableList.of(effect1, effect2);
        logLikelihood1 = -1;
        logLikelihood2 = -2;
        score1 = new SafeObjectVector(new double[]{ -3, -4 }, effects);
        score2 = new SafeObjectVector(new double[]{ -5, -6 }, effects);
        info1 = new SafeObjectMatrix(new double[][]{
                { 2, -1 },
                {-1,  2 },
        }, effects, effects);
        info2 = new SafeObjectMatrix(new double[][]{
                { 3,  1 },
                { 1,  3 },
        }, effects, effects);

        when(derivatives1.getLogLikelihood()).thenReturn(logLikelihood1);
        when(derivatives2.getLogLikelihood()).thenReturn(logLikelihood2);
        when(derivatives1.getScore()).thenReturn(score1);
        when(derivatives2.getScore()).thenReturn(score2);
        when(derivatives1.getInformation()).thenReturn(info1);
        when(derivatives2.getInformation()).thenReturn(info2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void noSampleThrow() {
        ObservedLikelihoodDerivativesCalculator underStudy = new ObservedLikelihoodDerivativesCalculator();
        underStudy.calculate(new ArrayList<LikelihoodDerivatives>(), effects);
    }

    public void calculate() {
        ObservedLikelihoodDerivativesCalculator underStudy = new ObservedLikelihoodDerivativesCalculator();
        LikelihoodDerivatives result = underStudy.calculate(completeDataDerivatives, effects);
        assertThat(result.getLogLikelihood()).isEqualTo(-1.5, tolerance);
        assertThat(result.getScore().getEntry(0)).isEqualTo(-4d, tolerance);
        assertThat(result.getScore().getEntry(1)).isEqualTo(-5d, tolerance);
        assertThat(result.getInformation().getEntry(0,0)).isEqualTo( 1.5 , tolerance);
        assertThat(result.getInformation().getEntry(0,1)).isEqualTo(-1.0, tolerance);
        assertThat(result.getInformation().getEntry(1,0)).isEqualTo(-1.0, tolerance);
        assertThat(result.getInformation().getEntry(1,1)).isEqualTo( 1.5, tolerance);
    }
}
