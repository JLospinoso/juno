package net.lospi.juno.estimation.ml;

/**
 * Created by jalospinoso on 6/20/2015.
 */
public interface MaximumLikelihoodResults {
    boolean isSuccessful();

    PhaseThreeResults getPhaseThreeResults();

    PhaseTwoResults getPhaseTwoResults();

    PhaseOneResults getPhaseOneResults();
}
