package net.lospi.juno.estimation.ml;

import java.util.ArrayList;
import java.util.List;

public class SingleThreadedRobbinsMonroPlan implements RobbinsMonroPlan {
    protected static final double INITIAL_GAIN = 0.2d;
    private final List<Integer> minimumIterations;
    private final List<Integer> maximumIterations;
    private final List<Double> gain;
    private final int phaseCount;

    public SingleThreadedRobbinsMonroPlan(int phaseCount, int parameterCount) {
        this.phaseCount = phaseCount;
        gain = new ArrayList<Double>();
        maximumIterations = new ArrayList<Integer>();
        minimumIterations = new ArrayList<Integer>();
        for(int phaseIndex=0; phaseIndex<phaseCount; phaseIndex++) {
            gain.add(phaseIndex, INITIAL_GAIN / Math.pow(2d, phaseIndex));
            minimumIterations.add((int) Math.ceil(Math.pow(2.52d, phaseIndex + 1d) * (7d + parameterCount)));
            maximumIterations.add(minimumIterations(phaseIndex) + 200);
        }
    }

    @Override
    public double gain(int phase) {
        check(phase);
        return gain.get(phase);
    }

    @Override
    public int minimumIterations(int phase) {
        check(phase);
        return minimumIterations.get(phase);
    }

    @Override
    public int maximumIterations(int phase) {
        check(phase);
        return maximumIterations.get(phase);
    }

    @Override
    public int phaseCount() {
        return phaseCount;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("   #   min   max   gain");
        for(int phaseIndex=0; phaseIndex<phaseCount; phaseIndex++) {
            builder.append(String.format("%n %3d %5d %5d %6.4f",
                    phaseIndex,
                    minimumIterations.get(phaseIndex),
                    maximumIterations.get(phaseIndex),
                    gain.get(phaseIndex)));
        }
        return builder.toString();
    }

    private void check(int phase) {
        if(phase < 0 || phase >= phaseCount()) {
            throw new IllegalArgumentException(String.format("Phase must be on the interval [0,%s).", phaseCount()));
        }
    }
}
