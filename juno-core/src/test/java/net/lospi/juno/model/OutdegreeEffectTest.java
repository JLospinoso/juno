package net.lospi.juno.model;

import net.lospi.juno.elements.Network;
import org.testng.annotations.BeforeMethod;
import net.lospi.juno.elements.State;
import org.testng.annotations.Test;
import net.lospi.juno.elements.ActorAspect;
import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.lang.String;

@Test(groups="unit")
public class OutdegreeEffectTest {
    private State state;
    private String networkName;
    private String ego, alter;
    private ActorAspect egoAspect;
    private Network network;

    @BeforeMethod
    public void setUp() {
        alter = "b";
        ego = "a";
        networkName = "my-network";
        egoAspect = mock(ActorAspect.class);
        network = mock(Network.class);
        state = mock(State.class);
        when(state.getNetwork(networkName)).thenReturn(network);
        when(egoAspect.getActor()).thenReturn(ego);
        when(egoAspect.getAspect()).thenReturn(networkName);
    }

    public void zeroOnDiagonal() {
        OutdegreeEffect underStudy = new OutdegreeEffect(networkName);
        double result = underStudy.statistic(egoAspect, ego, state);
        assertThat(result).isEqualTo(0);
    }

    public void zeroOnNonAspect() {
        OutdegreeEffect underStudy = new OutdegreeEffect(networkName);
        when(egoAspect.getAspect()).thenReturn("foo!");
        double result = underStudy.statistic(egoAspect, alter, state);
        assertThat(result).isEqualTo(0);
    }

    public void negativeOneWhenTiePresent() {
        OutdegreeEffect underStudy = new OutdegreeEffect(networkName);
        when(network.value("a", "b")).thenReturn(true);
        double result = underStudy.statistic(egoAspect, alter, state);
        assertThat(result).isEqualTo(-1);
    }

    public void oneWhenTieNotPresent() {
        OutdegreeEffect underStudy = new OutdegreeEffect(networkName);
        when(network.value("a", "b")).thenReturn(false);
        double result = underStudy.statistic(egoAspect, alter, state);
        assertThat(result).isEqualTo(1);
    }

    public void toStringNotNull() {
        OutdegreeEffect underStudy = new OutdegreeEffect(networkName);
        assertThat(underStudy.toString()).isNotNull();
    }

}
