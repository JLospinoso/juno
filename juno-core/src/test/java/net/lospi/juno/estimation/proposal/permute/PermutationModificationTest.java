/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.permute;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.ChainSegment;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class PermutationModificationTest {
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
        PermutationModification modification = new PermutationModification(start, end, logAlterSelectionProbability, identity, replacement);
        Chain state = mock(Chain.class);
        modification.modify(state);
        verify(state).replaceFrom(start, end, replacement);
        verifyNoMoreInteractions(state);
    }

    public void testIsIdentity() throws Exception {
        PermutationModification modification = new PermutationModification(start, end, logAlterSelectionProbability, identity, replacement);
        assertThat(modification.isIdentity()).isEqualTo(identity);
    }

    public void testGetStart() throws Exception {
        PermutationModification modification = new PermutationModification(start, end, logAlterSelectionProbability, identity, replacement);
        assertThat(modification.getStart()).isEqualTo(start);
    }

    public void testGetEnd() throws Exception {
        PermutationModification modification = new PermutationModification(start, end, logAlterSelectionProbability, identity, replacement);
        assertThat(modification.getEnd()).isEqualTo(end);
    }
    
    public void testGetLogAlterSelectionProbability() throws Exception {
        PermutationModification modification = new PermutationModification(start, end, logAlterSelectionProbability, identity, replacement);
        assertThat(modification.getLogAlterSelectionProbability()).isEqualTo(logAlterSelectionProbability);
    }

    public void testGetReplacement() throws Exception {
        PermutationModification modification = new PermutationModification(start, end, logAlterSelectionProbability, identity, replacement);
        assertThat(modification.getReplacement()).isEqualTo(replacement);
    }
}