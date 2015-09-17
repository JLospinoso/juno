/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.integration;

import net.lospi.juno.elements.*;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.mh.MetropolisHastingsStep;
import net.lospi.juno.estimation.mh.SimpleMetropolisHastingsIterator;
import net.lospi.juno.estimation.proposal.ccp.delete.CcpDeletionModification;
import net.lospi.juno.estimation.proposal.ccp.insert.CcpInsertionModification;
import net.lospi.juno.estimation.proposal.diagonal.delete.DiagonalNetworkMinistepDeletionModification;
import net.lospi.juno.estimation.proposal.diagonal.insert.DiagonalNetworkMinistepInsertionModification;
import net.lospi.juno.estimation.proposal.permute.PermutationModification;
import net.lospi.juno.model.CommonsModelBuilder;
import net.lospi.juno.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups = "integration")
@ContextConfiguration(locations = {"classpath*:PermutationCcpDiagonalMcmcTest.xml"})
public class PermutationCcpDiagonalOnlyMcmcTest extends AbstractTestNGSpringContextTests {
    @Autowired
    public SimpleMetropolisHastingsIterator simpleMetropolisHastingsIterator;
    @Autowired
    public CommonsModelBuilder builder;
    private State state;
    private Model model;
    private Chain chain;
    private double globalRate = 500;
    private int actorCount = 20;
    private List<ActorAspect> actorAspectList;
    private Link link;
    private ChainSegmentBuilder chainSegmentBuilder;
    private LikelihoodDerivatives derivatives;

    @BeforeMethod
    public void setUp(){
        ActorAspect actorAspect = new ActorAspect("a0", "r0");
        String alter = "a0";
        derivatives = mock(LikelihoodDerivatives.class);
        link = new Ministep(actorAspect, alter);
        link.setLikelihoodDerivatives(derivatives);
        SortedSet<String> actors = new TreeSet<String>();
        actorAspectList = new ArrayList<ActorAspect>(actorCount);
        for(int i=0; i< actorCount; i++){
            String actor = "actor"+i;
            actors.add(actor);
            actorAspectList.add(new ActorAspect(actor, "a0"));
        }
        state = new SimpleState(new SimpleNetwork(actors));
        chainSegmentBuilder = new ChainSegmentBuilder();
        chain = new SimpleChain(actorAspectList, state, chainSegmentBuilder);
        model = builder.newModel()
                .withGlobalRate(globalRate)
                .build();
    }

    public void chainHasAppropriateDistribution(){
        int steps = 10000;
        int iteration = 0;
        int permutationAcceptances = 0, ccpInsertionAcceptances = 0, ccpDeletionAcceptances = 0,
                diagonalInsertionAcceptances = 0, diagonalDeletionAcceptances = 0;
        double stateSizeSum = 0;
        while(iteration++<steps) {
            MetropolisHastingsStep step = simpleMetropolisHastingsIterator.next(chain, model);
            stateSizeSum += chain.getSize();
            if(step.isAccepted()){
                Class modificationClass = step.getProposal().getModification().getClass();
                if(modificationClass.equals(PermutationModification.class)){
                    permutationAcceptances++;
                }
                if(modificationClass.equals(CcpInsertionModification.class)){
                    ccpInsertionAcceptances++;
                }
                if(modificationClass.equals(CcpDeletionModification.class)){
                    ccpDeletionAcceptances++;
                }
                if(modificationClass.equals(DiagonalNetworkMinistepInsertionModification.class)){
                    diagonalInsertionAcceptances++;
                }
                if(modificationClass.equals(DiagonalNetworkMinistepDeletionModification.class)){
                    diagonalDeletionAcceptances++;
                }
            }
        }
        stateSizeSum /= steps;
        assertThat(permutationAcceptances).isGreaterThan(0).isLessThan(steps);
        assertThat(ccpInsertionAcceptances).isGreaterThan(0).isLessThan(steps);
        assertThat(ccpDeletionAcceptances).isGreaterThan(0).isLessThan(steps);
        assertThat(diagonalInsertionAcceptances).isGreaterThan(0).isLessThan(steps);
        assertThat(diagonalDeletionAcceptances).isGreaterThan(0).isLessThan(steps);
        assertThat(Math.abs(stateSizeSum)).isGreaterThan(0).isLessThan(globalRate);
    }
}
