package net.lospi.juno.estimation.elements;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.model.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class CompositeLikelihoodDerivativesBuilderTest {
    private LikelihoodDerivatives derivative1, derivative2;
    private List<Effect> effectsListA, effectsListB;
    private double logLikelihood1 = -1D, logLikelihood2 = -2D;
    private ObjectVector score1, score2;
    private ObjectMatrix information1, information2;
    private Effect effect1, effect2, effect3;

    @BeforeMethod
    public void setUp() {
        effect1 = mock(Effect.class);
        effect2 = mock(Effect.class);
        effect3 = mock(Effect.class);
        effectsListA = ImmutableList.of(effect1, effect2);
        effectsListB = ImmutableList.of(effect3);
        score1 = new SafeObjectVector(new double[] { 1D, 2D }, effectsListA);
        score2 = new SafeObjectVector(new double[] { 3D }, effectsListB);
        information1 = new SafeObjectMatrix(new double[][] {
                new double[] { 3d, 4d },
                new double[] { 5d, 6d }
        }, effectsListA, effectsListA);
        information2 = new SafeObjectMatrix(new double[][] {
                new double[] { 7d  }
        }, effectsListB, effectsListB);
        derivative1 = mock(LikelihoodDerivatives.class);
        derivative2 = mock(LikelihoodDerivatives.class);

        when(derivative1.getEffects()).thenReturn(effectsListA);
        when(derivative1.getScore()).thenReturn(score1);
        when(derivative1.getInformation()).thenReturn(information1);
        when(derivative1.getLogLikelihood()).thenReturn(logLikelihood1);
        when(derivative2.getEffects()).thenReturn(effectsListB);
        when(derivative2.getScore()).thenReturn(score2);
        when(derivative2.getInformation()).thenReturn(information2);
        when(derivative2.getLogLikelihood()).thenReturn(logLikelihood2);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void throwsWhenNoDerivatives() {
        CompositeLikelihoodDerivativesBuilder underStudy = new CompositeLikelihoodDerivativesBuilder();
        underStudy.with()
                .build();
    }

    public void returnsIdenticalDerivative() {
        CompositeLikelihoodDerivativesBuilder underStudy = new CompositeLikelihoodDerivativesBuilder();
        LikelihoodDerivatives result = underStudy.with()
                .derivatives(derivative1)
                .build();

        assertThat(result.getEffects()).isEqualTo(effectsListA);
        assertThat(result.getLogLikelihood()).isEqualTo(logLikelihood1);
        assertThat(result.getScore().getEntry(0)).isEqualTo(score1.getEntry(0));
        assertThat(result.getScore().getEntry(1)).isEqualTo(score1.getEntry(1));
        assertThat(result.getInformation().getEntry(0, 0)).isEqualTo(information1.getEntry(0, 0));
        assertThat(result.getInformation().getEntry(1, 0)).isEqualTo(information1.getEntry(1, 0));
        assertThat(result.getInformation().getEntry(0, 1)).isEqualTo(information1.getEntry(0, 1));
        assertThat(result.getInformation().getEntry(1, 1)).isEqualTo(information1.getEntry(1, 1));
    }

    public void returnsTwoDerivatives() {
        List<Effect> effectsListAB = new ArrayList<Effect>();
        effectsListAB.addAll(effectsListA);
        effectsListAB.addAll(effectsListB);

        CompositeLikelihoodDerivativesBuilder underStudy = new CompositeLikelihoodDerivativesBuilder();
        LikelihoodDerivatives result = underStudy.with()
                .derivatives(derivative1)
                .derivatives(derivative2)
                .build();

        assertThat(result.getEffects()).isEqualTo( effectsListAB);
        assertThat(result.getLogLikelihood()).isEqualTo(logLikelihood1 + logLikelihood2);
        assertThat(result.getScore().getEntry(0)).isEqualTo(score1.getEntry(0));
        assertThat(result.getScore().getEntry(1)).isEqualTo(score1.getEntry(1));
        assertThat(result.getScore().getEntry(2)).isEqualTo(score2.getEntry(0));
        assertThat(result.getInformation().getEntry(0, 0)).isEqualTo(information1.getEntry(0, 0));
        assertThat(result.getInformation().getEntry(1, 0)).isEqualTo(information1.getEntry(1, 0));
        assertThat(result.getInformation().getEntry(0, 1)).isEqualTo(information1.getEntry(0, 1));
        assertThat(result.getInformation().getEntry(1, 1)).isEqualTo(information1.getEntry(1, 1));
        assertThat(result.getInformation().getEntry(2, 2)).isEqualTo(information2.getEntry(0, 0));
    }
}
