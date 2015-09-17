/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.elements;

public class SimpleState implements State {
    private final Network network;

    public SimpleState(Network network) {
        this.network = network;
    }

    @Override
    public Network getNetwork(String aspect) {
        return network;
    }
}
