package net.lospi.juno.estimation.ml;

import net.lospi.juno.model.Effect;
import net.lospi.juno.model.Model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups = "unit")
public class PhaseTwoResultsBuilderTest {
    private String failureMessage;
    private Model model;
    private List<Effect> effects;

    @BeforeMethod
    public void setUp() throws Exception {
        model = mock(Model.class);
        failureMessage = "fail";
        effects = mock(List.class);
        when(model.getAllEffects()).thenReturn(effects);
    }

    public void success() throws Exception {
        PhaseTwoResultsBuilder builder = new PhaseTwoResultsBuilder();
        PhaseTwoResults result = builder.with().model(model).build();
        assertThat(result.isSuccessful()).isTrue();
        assertThat(result.getStatus()).isEqualTo("Completed");
        assertThat(result.getEstimatedModel()).isEqualTo(model);
    }

    public void failure() throws Exception {
        PhaseTwoResultsBuilder builder = new PhaseTwoResultsBuilder();
        PhaseTwoResults result = builder.with().failure(failureMessage).build();
        assertThat(result.getEstimatedModel()).isNull();
        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getStatus()).isEqualTo(failureMessage);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void forgetToSet() throws Exception {
        PhaseTwoResultsBuilder builder = new PhaseTwoResultsBuilder();
        builder.with().build();
    }
}