package net.lospi.juno.estimation.sim;

import net.lospi.juno.model.Model;

public interface ScoreSimulator extends ParametricProcess {
    void setSamplingInterval(int samplingInterval);
    void setModel(Model model);
}
