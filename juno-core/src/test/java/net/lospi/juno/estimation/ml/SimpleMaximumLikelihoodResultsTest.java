package net.lospi.juno.estimation.ml;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class SimpleMaximumLikelihoodResultsTest {
    private boolean successful;
    private PhaseTwoResults phaseTwoResults;
    private PhaseThreeResults phaseThreeResults;
    private PhaseOneResults phaseOneResults;

    @BeforeMethod
    public void setUp() {
        phaseTwoResults = mock(PhaseTwoResults.class);
        phaseThreeResults = mock(PhaseThreeResults.class);
        phaseOneResults = mock(PhaseOneResults.class);
        successful = true;
    }

    public void isSuccessful() {
        SimpleMaximumLikelihoodResults underStudy = new SimpleMaximumLikelihoodResults(successful, phaseOneResults, phaseTwoResults, phaseThreeResults);
        boolean result = underStudy.isSuccessful();
        assertThat(result).isEqualTo(successful);
    }

    public void getPhaseTwoResults() {
        SimpleMaximumLikelihoodResults underStudy = new SimpleMaximumLikelihoodResults(successful, phaseOneResults, phaseTwoResults, phaseThreeResults);
        PhaseTwoResults result = underStudy.getPhaseTwoResults();
        assertThat(result).isEqualTo(phaseTwoResults);
    }

    public void getPhaseOneResults() {
        SimpleMaximumLikelihoodResults underStudy = new SimpleMaximumLikelihoodResults(successful, phaseOneResults, phaseTwoResults, phaseThreeResults);
        PhaseOneResults result = underStudy.getPhaseOneResults();
        assertThat(result).isEqualTo(phaseOneResults);
    }

    public void getPhaseThreeResults() {
        SimpleMaximumLikelihoodResults underStudy = new SimpleMaximumLikelihoodResults(successful, phaseOneResults, phaseTwoResults, phaseThreeResults);
        PhaseThreeResults result = underStudy.getPhaseThreeResults();
        assertThat(result).isEqualTo(phaseThreeResults);
    }
}