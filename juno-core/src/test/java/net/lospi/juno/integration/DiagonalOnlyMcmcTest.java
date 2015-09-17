/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.integration;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.elements.*;
import net.lospi.juno.estimation.mh.MetropolisHastingsStep;
import net.lospi.juno.estimation.mh.SimpleMetropolisHastingsIterator;
import net.lospi.juno.estimation.proposal.diagonal.delete.DiagonalNetworkMinistepDeletionModification;
import net.lospi.juno.estimation.proposal.diagonal.insert.DiagonalNetworkMinistepInsertionModification;
import net.lospi.juno.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

import static org.fest.assertions.api.Assertions.assertThat;

@Test(groups = "integration")
@ContextConfiguration(locations = {"classpath*:DiagonalOnlyMcmcTest.xml"})
public class DiagonalOnlyMcmcTest extends AbstractTestNGSpringContextTests {
    @Autowired
    public SimpleMetropolisHastingsIterator simpleMetropolisHastingsIterator;
    @Autowired
    CommonsModelBuilder modelBuilder;

    private State state;
    private Model model;
    private Chain chain;
    private double globalRate = 25;
    private int actorCount = 1000;
    private List<ActorAspect> actorAspectList;
    private ChainSegmentBuilder chainSegmentBuilder;

    @BeforeMethod
    public void setUp(){
        chainSegmentBuilder = new ChainSegmentBuilder();
        SortedSet<String> actors = new TreeSet<String>();
        actorAspectList = new ArrayList<ActorAspect>(actorCount);
        for(int i=0; i< actorCount; i++){
            String actor = "actor"+i;
            actors.add(actor);
            actorAspectList.add(new ActorAspect(actor, "a0"));
        }
        state = new SimpleState(new SimpleNetwork(actors));
        chain = new SimpleChain(actorAspectList, state, chainSegmentBuilder);
        model = modelBuilder.newModel()
                .withGlobalRate(globalRate)
                .build();
    }

    public void chainHasAppropriateDistribution(){
        int steps = 100000;
        int iteration = 0;
        int insertionAcceptances = 0, deletionAcceptances = 0;
        double stateSizeSum = 0;
        while(iteration++<steps) {
            MetropolisHastingsStep step = simpleMetropolisHastingsIterator.next(chain, model);
            stateSizeSum += chain.getSize();
            Class proposalClass = step.getProposal().getModification().getClass();
            if(step.isAccepted()){
                if(proposalClass.equals(DiagonalNetworkMinistepInsertionModification.class)){
                    insertionAcceptances++;
                }
                if(proposalClass.equals(DiagonalNetworkMinistepDeletionModification.class)){
                    deletionAcceptances++;
                }
            }
        }
        stateSizeSum /= steps;
        assertThat(insertionAcceptances).isGreaterThan(0);
        assertThat(deletionAcceptances).isGreaterThan(0);
        assertThat(Math.abs(stateSizeSum - globalRate)).isLessThan(0.25);
    }
}
