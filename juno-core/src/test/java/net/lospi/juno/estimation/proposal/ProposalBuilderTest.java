/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class ProposalBuilderTest {
    private Modification modification;
    private double logAcceptanceProbability;

    @BeforeMethod
    public void setUp(){
        modification = mock(Modification.class);
        logAcceptanceProbability = -12345;
    }

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "You must supply a modification.")
    public void modificationNotSet() throws Exception {
        ProposalBuilder builder = new ProposalBuilder();
        builder.with().build();
    }

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "You must supply an acceptance probability.")
    public void logAcceptanceProbabilityNotSet() throws Exception {
        ProposalBuilder builder = new ProposalBuilder();
        builder.with().modification(modification).build();
    }

    public void build() throws Exception {
        ProposalBuilder builder = new ProposalBuilder();
        Proposal result = builder.with().modification(modification).logAcceptanceProbability(logAcceptanceProbability).build();
        assertThat(result.getLogAcceptanceProbability()).isEqualTo(logAcceptanceProbability);
        assertThat(result.getModification()).isEqualTo(modification);
    }

    public void reset() throws Exception {
        ProposalBuilder builder = new ProposalBuilder();
        Modification otherModification = mock(Modification.class);
        double otherProbability = -987654;
        builder.with().modification(otherModification).logAcceptanceProbability(otherProbability).build();
        Proposal result = builder.with().modification(modification).logAcceptanceProbability(logAcceptanceProbability).build();
        assertThat(result.getLogAcceptanceProbability()).isEqualTo(logAcceptanceProbability);
        assertThat(result.getModification()).isEqualTo(modification);
    }
}
