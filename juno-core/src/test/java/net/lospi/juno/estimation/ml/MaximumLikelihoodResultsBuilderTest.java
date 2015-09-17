package net.lospi.juno.estimation.ml;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups = "unit")
public class MaximumLikelihoodResultsBuilderTest {
    private PhaseOneResults phaseOneResults;
    private PhaseTwoResults phaseTwoResults;
    private PhaseThreeResults phaseThreeResults;

    @BeforeMethod
    public void setUp() throws Exception {
        phaseOneResults = mock(PhaseOneResults.class);
        phaseTwoResults = mock(PhaseTwoResults.class);
        phaseThreeResults = mock(PhaseThreeResults.class);

        when(phaseOneResults.isSuccessful()).thenReturn(true);
        when(phaseTwoResults.isSuccessful()).thenReturn(true);
        when(phaseThreeResults.isSuccessful()).thenReturn(true);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void phaseOneNoSet() throws Exception {
        MaximumLikelihoodResultsBuilder builder = new MaximumLikelihoodResultsBuilder();
        builder.with().build();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void phaseTwoNoSet() throws Exception {
        MaximumLikelihoodResultsBuilder builder = new MaximumLikelihoodResultsBuilder();
        builder.with()
                .phaseOneResults(phaseOneResults)
                .build();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void phaseThreeNoSet() throws Exception {
        MaximumLikelihoodResultsBuilder builder = new MaximumLikelihoodResultsBuilder();
        builder.with()
                .phaseOneResults(phaseOneResults)
                .phaseTwoResults(phaseTwoResults)
                .build();
    }

    public void phaseOneFailure() throws Exception {
        when(phaseOneResults.isSuccessful()).thenReturn(false);
        MaximumLikelihoodResultsBuilder builder = new MaximumLikelihoodResultsBuilder();
        MaximumLikelihoodResults result = builder.with()
                .phaseOneResults(phaseOneResults)
                .build();
        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getPhaseOneResults()).isEqualTo(phaseOneResults);
        assertThat(result.getPhaseTwoResults()).isNull();
        assertThat(result.getPhaseThreeResults()).isNull();
    }

    public void phaseTwoFailure() throws Exception {
        when(phaseTwoResults.isSuccessful()).thenReturn(false);
        MaximumLikelihoodResultsBuilder builder = new MaximumLikelihoodResultsBuilder();
        MaximumLikelihoodResults result = builder.with()
                .phaseOneResults(phaseOneResults)
                .phaseTwoResults(phaseTwoResults)
                .build();
        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getPhaseOneResults()).isEqualTo(phaseOneResults);
        assertThat(result.getPhaseTwoResults()).isEqualTo(phaseTwoResults);
        assertThat(result.getPhaseThreeResults()).isNull();
    }

    public void phaseThreeFailure() throws Exception {
        when(phaseThreeResults.isSuccessful()).thenReturn(false);
        MaximumLikelihoodResultsBuilder builder = new MaximumLikelihoodResultsBuilder();
        MaximumLikelihoodResults result = builder.with()
                .phaseOneResults(phaseOneResults)
                .phaseTwoResults(phaseTwoResults)
                .phaseThreeResults(phaseThreeResults)
                .build();
        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getPhaseOneResults()).isEqualTo(phaseOneResults);
        assertThat(result.getPhaseTwoResults()).isEqualTo(phaseTwoResults);
        assertThat(result.getPhaseThreeResults()).isEqualTo(phaseThreeResults);
    }

    public void phaseThreeSuccess() throws Exception {
        MaximumLikelihoodResultsBuilder builder = new MaximumLikelihoodResultsBuilder();
        MaximumLikelihoodResults result = builder.with()
                .phaseOneResults(phaseOneResults)
                .phaseTwoResults(phaseTwoResults)
                .phaseThreeResults(phaseThreeResults)
                .build();
        assertThat(result.isSuccessful()).isTrue();
        assertThat(result.getPhaseOneResults()).isEqualTo(phaseOneResults);
        assertThat(result.getPhaseTwoResults()).isEqualTo(phaseTwoResults);
        assertThat(result.getPhaseThreeResults()).isEqualTo(phaseThreeResults);
    }
}
