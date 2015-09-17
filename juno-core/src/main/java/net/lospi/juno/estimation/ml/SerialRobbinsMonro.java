package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.sim.ParametricProcess;
import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.ObjectVector;
import net.lospi.juno.model.SafeObjectVector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SerialRobbinsMonro implements RobbinsMonro {
    private static final Log LOG = LogFactory.getLog(SerialRobbinsMonro.class);
    private final RobbinsMonroResultsBuilder robbinsMonroResultsBuilder;
    private final SerialRobbinsMonroPhaseExecutor phaseExecutor;
    private final SerialRobbinsMonroUtil util;
    private ObjectVector root;

    public SerialRobbinsMonro(RobbinsMonroResultsBuilder robbinsMonroResultsBuilder, SerialRobbinsMonroPhaseExecutor phaseExecutor,
                              SerialRobbinsMonroUtil util) {
        this.robbinsMonroResultsBuilder = robbinsMonroResultsBuilder;
        this.phaseExecutor = phaseExecutor;
        this.util = util;
        this.root = null;
    }

    @Override
    public RobbinsMonroResult optimize(ParametricProcess simulator, ObjectMatrix weight, RobbinsMonroPlan plan) {
        checkForDefaultRoot(simulator);
        ObjectVector solution = null;
        for(int phaseIndex = 0; phaseIndex < plan.phaseCount(); phaseIndex++) {
            double gain = plan.gain(phaseIndex);
            int minimumIterations = plan.minimumIterations(phaseIndex);
            int maximumIterations = plan.maximumIterations(phaseIndex);
            LOG.info(String.format("[+] Executing phase %s with gain %5.4f, %s minimum iterations, and %s maximum iterations",
                    phaseIndex, gain, minimumIterations, maximumIterations));
            solution = phaseExecutor.executeSubPhase(simulator, weight, root, gain, minimumIterations, maximumIterations);
            simulator.setParameters(solution);
            boolean healthyPhase = util.parametersAreHealthy(solution);
            if(!healthyPhase) {
                return robbinsMonroResultsBuilder.with()
                        .failure(String.format("[-] Robbins-Monro parameters became unhealthy during phase %s", phaseIndex))
                        .build();
            }
        }
        return robbinsMonroResultsBuilder.with()
                .solution(solution)
                .build();
    }

    public void setRoot(ObjectVector root) {
        this.root = root;
    }

    private void checkForDefaultRoot(ParametricProcess process) {
        if(root == null) {
            root = new SafeObjectVector(process.getIndex());
        }
    }
}
