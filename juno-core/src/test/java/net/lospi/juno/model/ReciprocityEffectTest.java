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
public class ReciprocityEffectTest {
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
        ReciprocityEffect underStudy = new ReciprocityEffect(networkName);
        double result = underStudy.statistic(egoAspect, ego, state);
        assertThat(result).isEqualTo(0);
    }

    public void zeroOnNonAspect() {
        ReciprocityEffect underStudy = new ReciprocityEffect(networkName);
        when(egoAspect.getAspect()).thenReturn("foo!");
        double result = underStudy.statistic(egoAspect, alter, state);
        assertThat(result).isEqualTo(0);
    }

    public void negativeOneWhenReciprocatedTiePresent() {
        ReciprocityEffect underStudy = new ReciprocityEffect(networkName);
        when(network.value(ego, alter)).thenReturn(true);
        when(network.value(alter, ego)).thenReturn(true);
        double result = underStudy.statistic(egoAspect, alter, state);
        assertThat(result).isEqualTo(-1);
    }

    public void oneWhenReciprocatedTieWouldForm() {
        ReciprocityEffect underStudy = new ReciprocityEffect(networkName);
        when(network.value(ego, alter)).thenReturn(false);
        when(network.value(alter, ego)).thenReturn(true);
        double result = underStudy.statistic(egoAspect, alter, state);
        assertThat(result).isEqualTo(1);
    }

    public void zeroWhenNoReciprocatedDyadTurnOff() {
        ReciprocityEffect underStudy = new ReciprocityEffect(networkName);
        when(network.value(ego, alter)).thenReturn(true);
        when(network.value(alter, ego)).thenReturn(false);
        double result = underStudy.statistic(egoAspect, alter, state);
        assertThat(result).isEqualTo(0);
    }

    public void zeroWhenNoTies() {
        ReciprocityEffect underStudy = new ReciprocityEffect(networkName);
        when(network.value(ego, alter)).thenReturn(false);
        when(network.value(alter, ego)).thenReturn(false);
        double result = underStudy.statistic(egoAspect, alter, state);
        assertThat(result).isEqualTo(0);
    }

    public void toStringNotNull() {
        ReciprocityEffect underStudy = new ReciprocityEffect(networkName);
        assertThat(underStudy.toString()).isNotNull();
    }
}
