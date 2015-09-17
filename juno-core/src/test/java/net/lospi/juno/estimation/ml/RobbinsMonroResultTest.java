package net.lospi.juno.estimation.ml;

import net.lospi.juno.model.ObjectVector;
import net.lospi.juno.model.SafeObjectVector;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class RobbinsMonroResultTest {
    private boolean success;
    private String status;
    private ObjectVector solution;

    @BeforeMethod
    public void setUp() {
        success = true;
        solution = mock(SafeObjectVector.class);
        status = "foo";
    }

    public void getStatus() {
        RobbinsMonroResult underStudy = new RobbinsMonroResult(solution, status, success);
        String result = underStudy.getStatus();
        assertThat(result).isEqualTo(status);
    }

    public void isSuccess() {
        RobbinsMonroResult underStudy = new RobbinsMonroResult(solution, status, success);
        boolean result = underStudy.isSuccessful();
        assertThat(result).isEqualTo(success);
    }

    public void getSolution() {
        RobbinsMonroResult underStudy = new RobbinsMonroResult(solution, status, success);
        ObjectVector result = underStudy.getSolution();
        assertThat(result).isEqualTo(solution);
    }

}