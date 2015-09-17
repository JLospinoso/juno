package net.lospi.juno.estimation.elements;

import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.ObjectVector;
import net.lospi.juno.model.SafeObjectMatrix;
import net.lospi.juno.model.SafeObjectVector;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class CommonsLikelihoodDerivativesTest {
    private double logLikelihood;
    private SafeObjectVector score;
    private SafeObjectMatrix information;
    private List effects;

    @BeforeMethod
    public void setUp() {
        logLikelihood = 12345d;
        score = mock(SafeObjectVector.class);
        information = mock(SafeObjectMatrix.class);
        effects = mock(List.class);
    }

    public void getEffects() {
        CommonsLikelihoodDerivatives underStudy = new CommonsLikelihoodDerivatives(score, information, effects, logLikelihood);
        List result = underStudy.getEffects();
        assertThat(result).isEqualTo(effects);
    }

    public void getScore() {
        CommonsLikelihoodDerivatives underStudy = new CommonsLikelihoodDerivatives(score, information, effects, logLikelihood);
        ObjectVector result = underStudy.getScore();
        assertThat(result).isEqualTo(score);
    }

    public void getLogLikelihood() {
        CommonsLikelihoodDerivatives underStudy = new CommonsLikelihoodDerivatives(score, information, effects, logLikelihood);
        double result = underStudy.getLogLikelihood();
        assertThat(result).isEqualTo(logLikelihood);
    }

    public void getInformation() {
        CommonsLikelihoodDerivatives underStudy = new CommonsLikelihoodDerivatives(score, information, effects, logLikelihood);
        ObjectMatrix result = underStudy.getInformation();
        assertThat(result).isEqualTo(information);
    }

}
