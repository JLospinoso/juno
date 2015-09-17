package net.lospi.juno.elements;

import com.google.common.collect.ImmutableSortedSet;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.SortedSet;

import static org.fest.assertions.api.Assertions.assertThat;

@Test(groups="unit")
public class SimpleNetworkTest {
    private String ego;
    private SortedSet actors;
    private String alter;

    @BeforeMethod
    public void setUp() {
        ego = "E";
        actors = ImmutableSortedSet.of("a", "b", "c", "E", "A");
        alter = "A";
    }

    public void flipTieDiagonal() {
        SimpleNetwork underStudy = new SimpleNetwork(actors);
        underStudy.flipTie(ego, ego);
        assertThat(underStudy.value(ego, ego)).isFalse();
    }

    public void flipTieOnce() {
        SimpleNetwork underStudy = new SimpleNetwork(actors);
        underStudy.flipTie(ego, alter);
        assertThat(underStudy.value(ego, alter)).isTrue();
    }

    public void flipTieTwice() {
        SimpleNetwork underStudy = new SimpleNetwork(actors);
        underStudy.flipTie(ego, alter);
        underStudy.flipTie(ego, alter);
        assertThat(underStudy.value(ego, alter)).isFalse();
    }

    public void getActorCount() {
        SimpleNetwork underStudy = new SimpleNetwork(actors);
        int result = underStudy.getActorCount();
        assertThat(result).isEqualTo(actors.size());
    }

    public void getActors() {
        SimpleNetwork underStudy = new SimpleNetwork(actors);
        Set result = underStudy.getActors();
        assertThat(result).isEqualTo(actors);
    }
}
