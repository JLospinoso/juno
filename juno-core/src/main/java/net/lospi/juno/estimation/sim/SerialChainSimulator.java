/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.sim;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.mh.MetropolisHastingsIterator;
import net.lospi.juno.estimation.mh.MetropolisHastingsStep;
import net.lospi.juno.model.Model;

public class SerialChainSimulator implements ChainSimulator {
    private final MetropolisHastingsIterator metropolisHastingsIterator;
    private final MetropolisHastingsSubscriber subscriber;
    private Chain chain;
    private Model model;

    public SerialChainSimulator(MetropolisHastingsIterator metropolisHastingsIterator, MetropolisHastingsSubscriber subscriber) {
        this.metropolisHastingsIterator = metropolisHastingsIterator;
        this.subscriber = subscriber;
    }

    @Override
    public void advance() {
        checkState();
        checkModel();
        MetropolisHastingsStep step = metropolisHastingsIterator.next(chain, model);
        subscriber.send(step, chain);
    }

    @Override
    public Chain getChain() {
        return chain;
    }

    @Override
    public boolean isChainSet() {
        return chain != null;
    }

    @Override
    public void setChain(Chain chain){
        this.chain = chain;
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    private void checkModel() {
        if(model == null){
            throw new IllegalStateException("You must set Model.");
        }
    }

    private void checkState() {
        if(chain == null){
            throw new IllegalStateException("You must set State.");
        }
    }
}
