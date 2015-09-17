/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.delete;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.ChainSegment;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.testng.Assert.*;

@Test(groups="unit")
public class CcpDeletionModificationTest {

    private Integer start;
    private Integer end;
    private Double logAlterSelectionProbability;
    private boolean identity;
    private ChainSegment replacement;

    @BeforeMethod
    public void setUp() throws Exception {
        start = 10;
        end = 20;
        logAlterSelectionProbability = 30d;
        identity = false;
        replacement = mock(ChainSegment.class);
    }

    public void testModify() throws Exception {
        CcpDeletionModification modification = new CcpDeletionModification(start, end, logAlterSelectionProbability, identity, replacement);
        Chain state = mock(Chain.class);
        modification.modify(state);
        verify(state).deleteAt(end);
        verify(state).deleteAt(start);
        verifyNoMoreInteractions(state);
    }

    public void testIsIdentity() throws Exception {
        CcpDeletionModification modification = new CcpDeletionModification(start, end, logAlterSelectionProbability, identity, replacement);
        assertThat(modification.isIdentity()).isEqualTo(identity);
    }

    public void testGetStart() throws Exception {
        CcpDeletionModification modification = new CcpDeletionModification(start, end, logAlterSelectionProbability, identity, replacement);
        assertThat(modification.getStart()).isEqualTo(start);
    }

    public void testGetEnd() throws Exception {
        CcpDeletionModification modification = new CcpDeletionModification(start, end, logAlterSelectionProbability, identity, replacement);
        assertThat(modification.getEnd()).isEqualTo(end);
    }

    public void testGetLogAlterSelectionProbability() throws Exception {
        CcpDeletionModification modification = new CcpDeletionModification(start, end, logAlterSelectionProbability, identity, replacement);
        assertThat(modification.getLogAlterSelectionProbability()).isEqualTo(logAlterSelectionProbability);
    }

    public void testGetReplacement() throws Exception {
        CcpDeletionModification modification = new CcpDeletionModification(start, end, logAlterSelectionProbability, identity, replacement);
        assertThat(modification.getReplacement()).isEqualTo(replacement);
    }
}