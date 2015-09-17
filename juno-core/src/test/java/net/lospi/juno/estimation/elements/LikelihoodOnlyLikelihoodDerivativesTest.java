package net.lospi.juno.estimation.elements;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

@Test(groups="unit")
public class LikelihoodOnlyLikelihoodDerivativesTest {
	private double logLikelihood;

	@BeforeMethod
	public void setUp() {
		logLikelihood = 12345D;
	}

		public void getLogLikelihood() {
		LikelihoodOnlyLikelihoodDerivatives underStudy = new LikelihoodOnlyLikelihoodDerivatives(logLikelihood);
		double result = underStudy.getLogLikelihood();
		assertThat(result).isEqualTo(logLikelihood);
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void getScore() {
		LikelihoodOnlyLikelihoodDerivatives underStudy = new LikelihoodOnlyLikelihoodDerivatives(logLikelihood);
		underStudy.getScore();
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void getInformation() {
		LikelihoodOnlyLikelihoodDerivatives underStudy = new LikelihoodOnlyLikelihoodDerivatives(logLikelihood);
		underStudy.getInformation();
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void getEffects() {
		LikelihoodOnlyLikelihoodDerivatives underStudy = new LikelihoodOnlyLikelihoodDerivatives(logLikelihood);
		underStudy.getEffects();
	}
}