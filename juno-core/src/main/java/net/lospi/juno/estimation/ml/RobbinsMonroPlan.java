package net.lospi.juno.estimation.ml;

/**
 * Created by jalospinoso on 6/20/2015.
 */
public interface RobbinsMonroPlan {
    double gain(int phase);

    int minimumIterations(int phase);

    int maximumIterations(int phase);

    int phaseCount();
}
