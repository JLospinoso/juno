/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.insert;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.elements.Ministep;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@Test(groups="unit")
public class DiagonalNetworkMinistepInsertionModificationTest {
    private Ministep ministep;
    private double logAlterSelectionProbability;
    private boolean identity;
    private int index;
    private Chain state;

    @BeforeMethod
    public void setUp(){
        ministep = mock(Ministep.class);
        logAlterSelectionProbability = -10D;
        identity = true;
        index = 10;
        state = mock(Chain.class);
    }

    public void canGetIndex(){
        DiagonalNetworkMinistepInsertionModification modification = new DiagonalNetworkMinistepInsertionModification(index, ministep, logAlterSelectionProbability, identity);
        assertThat(modification.getInsertionIndex()).isEqualTo(index);
    }

    public void canGetProbability(){
        DiagonalNetworkMinistepInsertionModification modification = new DiagonalNetworkMinistepInsertionModification(index, ministep, logAlterSelectionProbability, identity);
        assertThat(modification.getLogAlterSelectionProbability()).isEqualTo(logAlterSelectionProbability);
    }

    public void canModify(){
        DiagonalNetworkMinistepInsertionModification modification = new DiagonalNetworkMinistepInsertionModification(index, ministep, logAlterSelectionProbability, identity);
        assertThat(modification.getLogAlterSelectionProbability()).isEqualTo(logAlterSelectionProbability);
    }

    public void canGetMinistep(){
        DiagonalNetworkMinistepInsertionModification modification = new DiagonalNetworkMinistepInsertionModification(index, ministep, logAlterSelectionProbability, identity);
        modification.modify(state);
        verify(state).insertAt(index, ministep);
    }

    public void canGetIdentity(){
        DiagonalNetworkMinistepInsertionModification modification = new DiagonalNetworkMinistepInsertionModification(index, ministep, logAlterSelectionProbability, identity);
        assertThat(modification.isIdentity()).isEqualTo(identity);
    }
}
