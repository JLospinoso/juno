package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.elements.ConvergenceRatio;
import org.testng.annotations.BeforeMethod;
import net.lospi.juno.estimation.elements.ParameterCovariance;
import org.testng.annotations.Test;
import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.lang.String;

@Test(groups="unit")
public class SimplePhaseThreeResultsTest {
    private boolean successful;
    private String status;
    private ParameterCovariance parameterCovariance;
    private ConvergenceRatio tRatios;

    @BeforeMethod
    public void setUp() {
        parameterCovariance = mock(ParameterCovariance.class);
        status = "Status";
        tRatios = mock(ConvergenceRatio.class);
        successful = true;
    }
    public void getTRatios() {
        SimplePhaseThreeResults underStudy = new SimplePhaseThreeResults(status, successful, parameterCovariance, tRatios);
        ConvergenceRatio result = underStudy.getConvergenceRatio();
        assertThat(result).isEqualTo(tRatios);
    }

    public void getStatus() {
        SimplePhaseThreeResults underStudy = new SimplePhaseThreeResults(status, successful, parameterCovariance, tRatios);
        String result = underStudy.getStatus();
        assertThat(result).isEqualTo(status);
    }

    public void getParameterCovariance() {
        SimplePhaseThreeResults underStudy = new SimplePhaseThreeResults(status, successful, parameterCovariance, tRatios);
        ParameterCovariance result = underStudy.getParameterCovariance();
        assertThat(result).isEqualTo(parameterCovariance);
    }

    public void isSuccessful() {
        SimplePhaseThreeResults underStudy = new SimplePhaseThreeResults(status, successful, parameterCovariance, tRatios);
        boolean result = underStudy.isSuccessful();
        assertThat(result).isEqualTo(successful);
    }

    public void canToString() {
        SimplePhaseThreeResults underStudy = new SimplePhaseThreeResults(status, successful, parameterCovariance, tRatios);
        assertThat(underStudy.toString()).isNotNull();
    }
}
