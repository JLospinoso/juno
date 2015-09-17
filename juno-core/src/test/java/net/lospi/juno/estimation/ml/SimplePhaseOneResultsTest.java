package net.lospi.juno.estimation.ml;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.lang.String;

@Test(groups="unit")
public class SimplePhaseOneResultsTest {
    private WeightMatrix weightMatrix;
    private boolean success;
    private String status;

    @BeforeMethod
    public void setUp() {
        success = true;
        status = "foo";
        weightMatrix = mock(WeightMatrix.class);
    }

    public void getStatus() {
        SimplePhaseOneResults underStudy = new SimplePhaseOneResults(status, success, weightMatrix);
        String result = underStudy.getStatus();
        assertThat(result).isEqualTo(status);
    }

    public void isSuccessful() {
        SimplePhaseOneResults underStudy = new SimplePhaseOneResults(status, success, weightMatrix);
        boolean result = underStudy.isSuccessful();
        assertThat(result).isTrue();
    }

    public void getWeightMatrix() {
        SimplePhaseOneResults underStudy = new SimplePhaseOneResults(status, success, weightMatrix);
        WeightMatrix result = underStudy.getWeightMatrix();
        assertThat(result).isEqualTo(weightMatrix);
    }

    public void canToString() {
        SimplePhaseOneResults underStudy = new SimplePhaseOneResults(status, success, weightMatrix);
        String result = underStudy.toString();
        assertThat(result).isNotNull();
    }

}
