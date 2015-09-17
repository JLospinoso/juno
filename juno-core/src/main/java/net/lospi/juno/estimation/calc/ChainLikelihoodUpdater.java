package net.lospi.juno.estimation.calc;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.model.Model;

/**
 * Created by jalospinoso on 6/13/2015.
 */
public interface ChainLikelihoodUpdater {
    void updateLikelihoods(Chain chain, Model model);

    void updateScores(Chain chain, Model model);

    void updateInformation(Chain chain, Model model);
}
