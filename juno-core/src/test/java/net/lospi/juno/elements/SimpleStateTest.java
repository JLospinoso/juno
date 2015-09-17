package net.lospi.juno.elements;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class SimpleStateTest {
    private String aspect;
    private Network network;

    @BeforeMethod
    public void setUp() {
        aspect = "aspect";
        network = mock(Network.class);
    }

    public void getNetwork() {
        SimpleState underStudy = new SimpleState(network);
        Network result = underStudy.getNetwork(aspect);
        assertThat(result).isEqualTo(network);
    }
}
