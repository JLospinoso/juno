package net.lospi.juno.estimation.ml;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.lang.String;
import net.lospi.juno.model.Model;

@Test(groups="unit")
public class PhaseTwoResultsTest {
    private boolean successful;
    private String status;
    private Model estimatedModel;

    @BeforeMethod
    public void setUp() {
        estimatedModel = mock(Model.class);
        status = "foo";
        successful = true;
    }

    public void getEstimatedModel() {
        PhaseTwoResults underStudy = new PhaseTwoResults(estimatedModel, successful, status);
        Model result = underStudy.getEstimatedModel();
        assertThat(result).isEqualTo(estimatedModel);
    }

    public void isSuccessful() {
        PhaseTwoResults underStudy = new PhaseTwoResults(estimatedModel, successful, status);
        boolean result = underStudy.isSuccessful();
        assertThat(result).isEqualTo(successful);
    }

    public void getStatus() {
        PhaseTwoResults underStudy = new PhaseTwoResults(estimatedModel, successful, status);
        String result = underStudy.getStatus();
        assertThat(result).isEqualTo(status);
    }

    public void canToString() {
        PhaseTwoResults underStudy = new PhaseTwoResults(estimatedModel, successful, status);
        assertThat(underStudy.toString()).isNotNull();
    }
}