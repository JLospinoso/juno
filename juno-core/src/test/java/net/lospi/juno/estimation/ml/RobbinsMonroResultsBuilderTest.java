package net.lospi.juno.estimation.ml;

import net.lospi.juno.model.ObjectVector;
import net.lospi.juno.model.SafeObjectVector;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups = "unit")
public class RobbinsMonroResultsBuilderTest {
    private String failureMessage;
    private ObjectVector solution;

    @BeforeMethod
    public void setUp() throws Exception {
        solution = mock(SafeObjectVector.class);
        failureMessage = "fail";
    }

    public void success() throws Exception {
        RobbinsMonroResultsBuilder builder = new RobbinsMonroResultsBuilder();
        RobbinsMonroResult result = builder.with().solution(solution).build();
        assertThat(result.getSolution()).isEqualTo(solution);
        assertThat(result.isSuccessful()).isTrue();
        assertThat(result.getStatus()).isEqualTo("Solution found");
    }

    public void failure() throws Exception {
        RobbinsMonroResultsBuilder builder = new RobbinsMonroResultsBuilder();
        RobbinsMonroResult result = builder.with().failure(failureMessage).build();
        assertThat(result.getSolution()).isNull();
        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getStatus()).isEqualTo(failureMessage);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void forgetToSet() throws Exception {
        RobbinsMonroResultsBuilder builder = new RobbinsMonroResultsBuilder();
        builder.with().build();
    }
}