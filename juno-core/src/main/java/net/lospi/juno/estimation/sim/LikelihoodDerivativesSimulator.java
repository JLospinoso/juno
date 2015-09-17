package net.lospi.juno.estimation.sim;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.model.Model;

import java.util.List;

public interface LikelihoodDerivativesSimulator {
    List<LikelihoodDerivatives> simulateLikelihoodDerivatives(Model model, int samples);
    void setSamplingInterval(int samplingInterval);
    void burnIn(Model model, int samples);

    void setChain(Chain chain);
}
