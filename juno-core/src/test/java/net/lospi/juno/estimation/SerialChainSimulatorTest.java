/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.mh.MetropolisHastingsIterator;
import net.lospi.juno.estimation.mh.MetropolisHastingsStep;
import net.lospi.juno.estimation.proposal.Modification;
import net.lospi.juno.estimation.proposal.Proposal;
import net.lospi.juno.estimation.sim.MetropolisHastingsSubscriber;
import net.lospi.juno.estimation.sim.SerialChainSimulator;
import net.lospi.juno.model.Model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class SerialChainSimulatorTest {
    private Model model;
    private MetropolisHastingsIterator metropolisHastingsIterator;
    private Chain state;
    private MetropolisHastingsStep result1, result2;
    private Proposal proposal;
    private Modification modification;
    private MetropolisHastingsSubscriber subscriber;

    @BeforeMethod
    public void setUp(){
        model = mock(Model.class);
        metropolisHastingsIterator = mock(MetropolisHastingsIterator.class);
        state = mock(Chain.class);
        result1 = mock(MetropolisHastingsStep.class);
        result2 = mock(MetropolisHastingsStep.class);
        proposal = mock(Proposal.class);
        modification = mock(Modification.class);
        subscriber = mock(MetropolisHastingsSubscriber.class);
        when(metropolisHastingsIterator.next(state, model)).thenReturn(result1).thenReturn(result2);
    }

    public void testAdvance() throws Exception {
        SerialChainSimulator simulator = new SerialChainSimulator(metropolisHastingsIterator, subscriber);
        simulator.setModel(model);
        simulator.setChain(state);
        simulator.advance();
        simulator.advance();
        verify(subscriber).send(result1, state);
        verify(subscriber).send(result2, state);
    }

    public void testGetState() throws Exception {
        SerialChainSimulator simulator = new SerialChainSimulator(metropolisHastingsIterator, subscriber);
        simulator.setModel(model);
        simulator.setChain(state);
        assertThat(simulator.getChain()).isEqualTo(state);
    }

    public void isChainSetTrue() {
        SerialChainSimulator simulator = new SerialChainSimulator(metropolisHastingsIterator, subscriber);
        simulator.setChain(state);
        assertThat(simulator.isChainSet()).isTrue();
    }

    public void isChainSetFalse() {
        SerialChainSimulator simulator = new SerialChainSimulator(metropolisHastingsIterator, subscriber);
        assertThat(simulator.isChainSet()).isFalse();
    }

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "You must set Model.")
    public void testSetModel() throws Exception {
        SerialChainSimulator simulator = new SerialChainSimulator(metropolisHastingsIterator, subscriber);
        simulator.setChain(state);
        simulator.advance();
    }

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "You must set State.")
    public void testSetState() throws Exception {
        SerialChainSimulator simulator = new SerialChainSimulator(metropolisHastingsIterator, subscriber);
        simulator.setModel(model);
        simulator.advance();
    }
}
