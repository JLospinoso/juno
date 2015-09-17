/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.integration;

import net.lospi.juno.elements.*;
import net.lospi.juno.estimation.calc.SimpleChainLikelihoodUpdater;
import net.lospi.juno.estimation.ml.MaximumLikelihoodEstimator;
import net.lospi.juno.estimation.ml.MaximumLikelihoodResults;
import net.lospi.juno.estimation.ml.RobbinsMonroPlan;
import net.lospi.juno.estimation.ml.SingleThreadedRobbinsMonroPlan;
import net.lospi.juno.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.lospi.juno.util.JunoTestUtilities.resource;
import static org.fest.assertions.api.Assertions.assertThat;

@Test(groups = "integration", enabled = false)
@ContextConfiguration(locations = {"classpath*:SerialMlEstimationIntegrationTest.xml"})
public class SerialMlEstimationIntegrationTest extends AbstractTestNGSpringContextTests {
    private static final Log LOGGER = LogFactory.getLog(SerialMlEstimationIntegrationTest.class);
    @Autowired
    MaximumLikelihoodEstimator estimator;
    @Autowired
    public CommonsModelBuilder modelBuilder;
    @Autowired
    private SimpleChainLikelihoodUpdater simpleChainLikelihoodUpdater;
    @Autowired
    ChainSegmentBuilder chainSegmentBuilder;
    @Autowired
    FileNetworkParser fileNetworkParser;

    private Model model;
    private SimpleChain initialChain;
    private RobbinsMonroPlan plan;

    String networkName = "friendship";
    // FROM MoM:
    double initialOutdegree =  -2.2409d;
    double initialReciprocity = 2.4548d;
    double initialRate = 5.4591d * 50d;

    int burnIn = 250;
    int samplingInterval = 500; // 5 is "Multiplication factor", interval = factor x difference in observations.
    int drawsPerPhaseTwoEstimation = 1;
    int phaseTwoIntervals = 4;
    int phaseThreeSamples = 1000;

    @BeforeMethod
    public void setUp() throws FileNotFoundException {
        AlterSelectionEffect outdegree = new OutdegreeEffect(networkName);
        AlterSelectionEffect reciprocity = new ReciprocityEffect(networkName);
        Network s501 = fileNetworkParser.parse(resource("data\\s501.csv"), networkName);
        Network s502 = fileNetworkParser.parse(resource("data\\s502.csv"), networkName);
        State state = new SimpleState(s501);
        List<ActorAspect> actorAspects = new ArrayList<ActorAspect>(s501.getActorCount());
        for(String actor : s501.getActors()) {
            actorAspects.add(new ActorAspect(actor, networkName));
        }

        initialChain = new SimpleChain(actorAspects, state, chainSegmentBuilder);
        for(ActorAspect ego : actorAspects) {
            for(ActorAspect alter : actorAspects) {
                if(s501.value(ego.getActor(), alter.getActor()) != s502.value(ego.getActor(), alter.getActor())) {
                    initialChain.append(new Ministep(ego, alter.getActor()));
                }
            }
        }

        model = modelBuilder.newModel()
                .withEffect(outdegree,  initialOutdegree)
                .withEffect(reciprocity, initialReciprocity)
                .withGlobalRate(initialRate)
                .build();

        simpleChainLikelihoodUpdater.updateLikelihoods(initialChain, model);
        plan = new SingleThreadedRobbinsMonroPlan(phaseTwoIntervals, model.getAllEffectsSize());
        estimator.setSamplingInterval(samplingInterval);
        estimator.setPhaseOneBurnIn(burnIn);
        estimator.setDrawsPerPhaseTwoSample(drawsPerPhaseTwoEstimation);
        estimator.setPhaseThreeSamples(phaseThreeSamples);
    }

    public void executesWithoutError(){
        LOGGER.info(String.format("Initial chain contains %d links.", initialChain.getSize()));
        LOGGER.info(String.format("(Expected) Model:%n%s", model.toString()));
        LOGGER.info(String.format("Robbins-Monro Plan:%n%s", plan.toString()));
        MaximumLikelihoodResults result = estimator.estimate(model, initialChain, plan);

        LOGGER.info(String.format("Estimated model: %n%s", result.getPhaseTwoResults().getEstimatedModel().toString()));
        LOGGER.info(String.format("Covariance matrix: %n%s", result.getPhaseThreeResults().getParameterCovariance()));
        LOGGER.info(String.format("Convergence Ratios: %n%s", result.getPhaseThreeResults().getConvergenceRatio()));
        LOGGER.info(String.format("Chain length upon completion: %d", initialChain.getSize()));
        assertThat(result.getPhaseOneResults().isSuccessful()).isTrue();
        assertThat(result.getPhaseTwoResults().isSuccessful()).isTrue();
        assertThat(result.getPhaseThreeResults().isSuccessful()).isTrue();
    }
}
