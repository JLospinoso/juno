/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.delete;

import net.lospi.juno.elements.Chain;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@Test(groups="unit")
public class DiagonalNetworkMinistepDeletionModificationTest {
    private int index;
    private double alterSelectionProb;
    private boolean identity;
    private Chain state;

    @BeforeMethod
    public void setUp(){
        index = 10;
        alterSelectionProb = 20;
        identity = true;
        state = mock(Chain.class);
    }

    public void canGetIndex(){
        DiagonalNetworkMinistepDeletionModification modification = new DiagonalNetworkMinistepDeletionModification(index, alterSelectionProb, identity);
        assertThat(modification.getDeletionIndex()).isEqualTo(10);
    }

    public void canGetAlterSelectionProbability(){
        DiagonalNetworkMinistepDeletionModification modification = new DiagonalNetworkMinistepDeletionModification(index, alterSelectionProb, identity);
        assertThat(modification.getLogAlterSelectionProbability()).isEqualTo(alterSelectionProb);
    }

    public void canGetIdentity(){
        DiagonalNetworkMinistepDeletionModification modification = new DiagonalNetworkMinistepDeletionModification(index, alterSelectionProb, identity);
        assertThat(modification.isIdentity()).isEqualTo(identity);
    }

    public void canModify(){
        DiagonalNetworkMinistepDeletionModification modification = new DiagonalNetworkMinistepDeletionModification(index, alterSelectionProb, identity);
        modification.modify(state);
        verify(state).deleteAt(index);
    }
}
