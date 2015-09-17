package net.lospi.juno.estimation.calc;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.Ministep;
import net.lospi.juno.elements.State;
import net.lospi.juno.model.Model;

public class SimpleChainLikelihoodUpdater implements ChainLikelihoodUpdater {
    private final LinkLikelihoodDerivativesCalculator<Ministep> ministepLinkLikelihoodDerivativesCalculator;

    public SimpleChainLikelihoodUpdater(LinkLikelihoodDerivativesCalculator<Ministep> ministepLinkLikelihoodDerivativesCalculator) {
        this.ministepLinkLikelihoodDerivativesCalculator = ministepLinkLikelihoodDerivativesCalculator;
    }

    @Override
    public void updateLikelihoods(Chain chain, Model model) {
        apply(new UpdateFunction() {
            @Override
            public void update(Ministep ministep, State state, Model model) {
                ministepLinkLikelihoodDerivativesCalculator.updateLikelihood(ministep, state, model);
            }
        }, chain, model);
    }

    @Override
    public void updateScores(Chain chain, Model model) {
        apply(new UpdateFunction() {
            @Override
            public void update(Ministep ministep, State state, Model model) {
                ministepLinkLikelihoodDerivativesCalculator.updateLikelihoodAndScore(ministep, state, model);
            }
        }, chain, model);
    }


    @Override
    public void updateInformation(Chain chain, Model model) {
        apply(new UpdateFunction() {
            @Override
            public void update(Ministep ministep, State state, Model model) {
                ministepLinkLikelihoodDerivativesCalculator.updateLikelihoodScoreAndInformation(ministep, state, model);
            }
        }, chain, model);
    }

    private interface UpdateFunction {
        void update(Ministep ministep, State state, Model model);
    }

    private void apply(UpdateFunction function, Chain chain, Model model) {
        for(int index = 0; index < chain.getSize(); index++) {
            State state = chain.stateAt(index-1);
            Ministep ministep = (Ministep) chain.getAt(index);
            function.update(ministep, state, model);
        }
    }
}
