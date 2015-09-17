package net.lospi.juno.integration;

import net.lospi.juno.estimation.ml.*;
import net.lospi.juno.estimation.sim.ParametricProcess;
import net.lospi.juno.estimation.util.RealVectorUtil;
import net.lospi.juno.model.*;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well44497b;
import org.fest.assertions.data.Offset;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.offset;

//TODO: Springify test?
@Test(groups = "integration")
public class RobbinsMonroTest {
    private SafeObjectVector trueParameters;
    private ObjectVector initialSolution;
    private ParametricProcess process;
    private ObjectMatrix weightMatrix;
    private RobbinsMonroResultsBuilder robbinsMonroResultsBuilder;
    private RobbinsMonroPlan plan;
    private Offset<Double> tolerance;
    private SerialRobbinsMonroPhaseExecutor phaseExecutor;
    private SerialRobbinsMonroUtil util;

    @BeforeMethod
    public void setUp() throws Exception {
        int seed = 0;
        int samples = 1000;
        RealVectorUtil realVectorUtil = new RealVectorUtil();
        util = new SerialRobbinsMonroUtil(realVectorUtil);
        RandomGenerator randomGenerator = new Well44497b(seed); //TODO: Use colt instead
        trueParameters = new SafeObjectVector(new double[]{5D, 1D}, SimpleRobbinsMonroProcess.INDEX);
        initialSolution = new SafeObjectVector(new double[]{25D, 3D}, SimpleRobbinsMonroProcess.INDEX);
        process = new SimpleRobbinsMonroProcess(samples, initialSolution, randomGenerator);
        robbinsMonroResultsBuilder = new RobbinsMonroResultsBuilder();
        SerialRobbinsMonroUtil serialRobbinsMonroUtil = new SerialRobbinsMonroUtil(realVectorUtil);
        StreamingRealVectorCalculator streamingRealVectorCalculator = new StreamingRealVectorCalculator();
        weightMatrix = new SafeObjectMatrix(new double[][]{
                new double[]{1D, 0D},
                new double[]{0D, 1D}
        }, SimpleRobbinsMonroProcess.INDEX, SimpleRobbinsMonroProcess.INDEX);
        plan = new SingleThreadedRobbinsMonroPlan(6, 2);
        tolerance = offset(0.01D);
        phaseExecutor = new SerialRobbinsMonroPhaseExecutor(serialRobbinsMonroUtil, streamingRealVectorCalculator);
    }

    public void testOptimize() throws Exception {
        SerialRobbinsMonro robbinsMonro = new SerialRobbinsMonro(robbinsMonroResultsBuilder, phaseExecutor, util);
        robbinsMonro.setRoot(trueParameters);
        RobbinsMonroResult result = robbinsMonro.optimize(process, weightMatrix, plan);
        assertThat(result.isSuccessful()).isTrue();
        assertThat(result.getSolution().getEntry(0)).isEqualTo(trueParameters.getEntry(0), tolerance);
        assertThat(result.getSolution().getEntry(1)).isEqualTo(trueParameters.getEntry(1), tolerance);
    }
}