package net.lospi.juno.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import net.lospi.juno.elements.Network;
import net.lospi.juno.stat.Statistics;
import org.testng.annotations.BeforeMethod;
import net.lospi.juno.stat.CommonsStatisticsBuilder;
import net.lospi.juno.elements.State;
import org.testng.annotations.Test;
import net.lospi.juno.elements.ActorAspect;
import java.util.List;
import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class CommonsAlterSelectionStatisticsCalculatorTest {
    private CommonsStatisticsBuilder builder;
    private State state;
    private List effects;
    private ActorAspect egoAspect;
    private Statistics expected;
    private CommonsStatisticsBuilder.Stub stub;
    private String aspect, actor1, actor2;
    private Network network;
    private AlterSelectionEffect effect1, effect2;
    @BeforeMethod
    public void setUp() {
        stub = mock(CommonsStatisticsBuilder.Stub.class);
        expected = mock(Statistics.class);
        egoAspect = mock(ActorAspect.class);
        state = mock(State.class);
        effect1 = mock(AlterSelectionEffect.class);
        effect2 = mock(AlterSelectionEffect.class);
        effects = ImmutableList.of(effect1, effect2);
        builder = mock(CommonsStatisticsBuilder.class);
        aspect = "aspect";
        actor1 = "a";
        actor2 = "b";
        network = mock(Network.class);

        when(egoAspect.getAspect()).thenReturn(aspect);
        when(state.getNetwork(aspect)).thenReturn(network);
        when(builder.with()).thenReturn(stub);
        when(stub.build()).thenReturn(expected);
        when(stub.effectsOrder(effects)).thenReturn(stub);
        when(network.getActors()).thenReturn(ImmutableSortedSet.of(actor1, actor2));

        when(effect1.statistic(egoAspect, actor1, state)).thenReturn(1D);
        when(effect1.statistic(egoAspect, actor2, state)).thenReturn(2D);
        when(effect2.statistic(egoAspect, actor1, state)).thenReturn(3D);
        when(effect2.statistic(egoAspect, actor2, state)).thenReturn(4D);

        when(stub.outcome(any(Effect.class), anyString(), anyDouble())).thenReturn(stub);
    }

    public void calculateStatistics() {
        CommonsAlterSelectionStatisticsCalculator underStudy = new CommonsAlterSelectionStatisticsCalculator(builder);
        Statistics result = underStudy.calculateStatistics(egoAspect, state, effects);
        assertThat(result).isEqualTo(expected);

        verify(stub).effectsOrder(effects);
        verify(stub).outcome(effect1, actor1, 1D);
        verify(stub).outcome(effect1, actor2, 2D);
        verify(stub).outcome(effect2, actor1, 3D);
        verify(stub).outcome(effect2, actor2, 4D);
        verify(stub).build();
        verifyNoMoreInteractions(stub);
    }
}
