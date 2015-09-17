package net.lospi.juno.estimation.calc;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.model.RateEffect;
import net.lospi.juno.stat.CachedLogFactorial;
import net.lospi.juno.stat.CachedNaturalLogarithm;
import org.fest.assertions.data.Offset;
import org.testng.annotations.BeforeMethod;
import net.lospi.juno.elements.Chain;
import org.testng.annotations.Test;
import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;
import net.lospi.juno.model.Model;

import java.util.List;

@Test(groups="unit")
public class RateLikelihoodDerivativesCalculatorTest {
    private static final Double DOUBLE_COMPARISON_TOLERANCE = 1e-15;
    private CachedNaturalLogarithm naturalLogarithm;
    private Model model;
    private Chain chain;
    private int chainSize = 100;
    private double globalRate = 124;
    private List<RateEffect> rateEffects;
    private Offset<Double> tolerance = offset(DOUBLE_COMPARISON_TOLERANCE);
    private CachedLogFactorial logFactorial;

    @BeforeMethod
    public void setUp() {
        naturalLogarithm = mock(CachedNaturalLogarithm.class);
        logFactorial = mock(CachedLogFactorial.class);
        chain = mock(Chain.class);
        model = mock(Model.class);
        rateEffects = ImmutableList.of(mock(RateEffect.class));

        when(model.getRateEffects()).thenReturn(rateEffects);
        when(chain.getSize()).thenReturn(chainSize);
        when(model.globalRate()).thenReturn(globalRate);
        when(logFactorial.apply(chainSize)).thenReturn(10D);
        when(naturalLogarithm.apply(globalRate)).thenReturn(20D);
    }

    public void getEffects() {
        RateLikelihoodDerivativesCalculator underStudy = new RateLikelihoodDerivativesCalculator(logFactorial, naturalLogarithm);
        LikelihoodDerivatives result = underStudy.calculate(chain, model);
        assertThat(result.getEffects()).isEqualTo(rateEffects);
    }

    public void getInformation() {
        RateLikelihoodDerivativesCalculator underStudy = new RateLikelihoodDerivativesCalculator(logFactorial, naturalLogarithm);
        LikelihoodDerivatives result = underStudy.calculate(chain, model);
        assertThat(result.getInformation().getEntry(0, 0)).isEqualTo(-0.0065036420395421434D, tolerance);
        assertThat(result.getInformation().getRowDimension()).isEqualTo(1);
        assertThat(result.getInformation().getColumnDimension()).isEqualTo(1);
    }

    public void getScore() {
        RateLikelihoodDerivativesCalculator underStudy = new RateLikelihoodDerivativesCalculator(logFactorial, naturalLogarithm);
        LikelihoodDerivatives result = underStudy.calculate(chain, model);
        assertThat(result.getScore().getEntry(0)).isEqualTo(-0.19354838709677424, tolerance);
        assertThat(result.getScore().getDimension()).isEqualTo(1);
    }

    public void getLikelihood() {
        RateLikelihoodDerivativesCalculator underStudy = new RateLikelihoodDerivativesCalculator(logFactorial, naturalLogarithm);
        LikelihoodDerivatives result = underStudy.calculate(chain, model);
        assertThat(result.getLogLikelihood()).isEqualTo(1866.0, tolerance);
    }
}
