package net.lospi.juno.estimation.ml;

import org.fest.assertions.data.Offset;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.fest.assertions.api.Assertions.*;

@Test(groups="unit")
public class SingleThreadedRobbinsMonroPlanTest {
    private int phaseCount;
    private int phase;
    private int parameterCount;
    private Offset<Double> tolerance = offset(1e-15);

    @BeforeMethod
    public void setUp() {
        phaseCount = 4;
        parameterCount = 3;
    }
    public void minimumIterations() {
        SingleThreadedRobbinsMonroPlan underStudy = new SingleThreadedRobbinsMonroPlan(phaseCount, parameterCount);
        assertThat(underStudy.minimumIterations(0)).isEqualTo(26);
        assertThat(underStudy.minimumIterations(1)).isEqualTo(64);
        assertThat(underStudy.minimumIterations(2)).isEqualTo(161);
        assertThat(underStudy.minimumIterations(3)).isEqualTo(404);
    }

    public void maximumIterations() {
        SingleThreadedRobbinsMonroPlan underStudy = new SingleThreadedRobbinsMonroPlan(phaseCount, parameterCount);
        assertThat(underStudy.maximumIterations(0)).isEqualTo(226);
        assertThat(underStudy.maximumIterations(1)).isEqualTo(264);
        assertThat(underStudy.maximumIterations(2)).isEqualTo(361);
        assertThat(underStudy.maximumIterations(3)).isEqualTo(604);
    }

    public void gain() {
        SingleThreadedRobbinsMonroPlan underStudy = new SingleThreadedRobbinsMonroPlan(phaseCount, parameterCount);
        double initialGain = SingleThreadedRobbinsMonroPlan.INITIAL_GAIN;
        assertThat(underStudy.gain(0)).isEqualTo(initialGain, tolerance);
        assertThat(underStudy.gain(1)).isEqualTo(initialGain / 2, tolerance);
        assertThat(underStudy.gain(2)).isEqualTo(initialGain / 4, tolerance);
        assertThat(underStudy.gain(3)).isEqualTo(initialGain / 8, tolerance);
    }

    public void phaseCount() {
        SingleThreadedRobbinsMonroPlan underStudy = new SingleThreadedRobbinsMonroPlan(phaseCount, parameterCount);
        int result = underStudy.phaseCount();
        assertThat(result).isEqualTo(phaseCount);
    }

    public void toStringIsNotNull() {
        SingleThreadedRobbinsMonroPlan underStudy = new SingleThreadedRobbinsMonroPlan(phaseCount, parameterCount);
        assertThat(underStudy.toString()).isNotNull();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void throwsWithNegativePhase() {
        SingleThreadedRobbinsMonroPlan underStudy = new SingleThreadedRobbinsMonroPlan(phaseCount, parameterCount);
        underStudy.gain(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void throwsWithOutOfBoundsPhase() {
        SingleThreadedRobbinsMonroPlan underStudy = new SingleThreadedRobbinsMonroPlan(phaseCount, parameterCount);
        underStudy.gain(phaseCount);
    }
}