/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.insert;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.ChainSegment;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class CcpInsertionModificationTest {
    private ActorAspect actorAspect;
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
        actorAspect = mock(ActorAspect.class);
    }

    public void testModify() throws Exception {
        CcpInsertionModification modification = new CcpInsertionModification(start, end, logAlterSelectionProbability, identity, replacement, actorAspect);
        Chain state = mock(Chain.class);
        modification.modify(state);
        verify(state).replaceFrom(start, end-2, replacement);
        verifyNoMoreInteractions(state);
    }

    public void testIsIdentity() throws Exception {
        CcpInsertionModification modification = new CcpInsertionModification(start, end, logAlterSelectionProbability, identity, replacement, actorAspect);
        assertThat(modification.isIdentity()).isEqualTo(identity);
    }

    public void testGetStart() throws Exception {
        CcpInsertionModification modification = new CcpInsertionModification(start, end, logAlterSelectionProbability, identity, replacement, actorAspect);
        assertThat(modification.getStart()).isEqualTo(start);
    }

    public void testGetEnd() throws Exception {
        CcpInsertionModification modification = new CcpInsertionModification(start, end, logAlterSelectionProbability, identity, replacement, actorAspect);
        assertThat(modification.getEnd()).isEqualTo(end);
    }

    public void testGetActorAspect() throws Exception {
        CcpInsertionModification modification = new CcpInsertionModification(start, end, logAlterSelectionProbability, identity, replacement, actorAspect);
        assertThat(modification.getActorAspect()).isEqualTo(actorAspect);
    }

    public void testGetLogAlterSelectionProbability() throws Exception {
        CcpInsertionModification modification = new CcpInsertionModification(start, end, logAlterSelectionProbability, identity, replacement, actorAspect);
        assertThat(modification.getLogAlterSelectionProbability()).isEqualTo(logAlterSelectionProbability);
    }

    public void testGetReplacement() throws Exception {
        CcpInsertionModification modification = new CcpInsertionModification(start, end, logAlterSelectionProbability, identity, replacement, actorAspect);
        assertThat(modification.getReplacement()).isEqualTo(replacement);
    }
}