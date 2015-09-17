/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.delete;

import net.lospi.juno.estimation.proposal.ChainSegment;
import net.lospi.juno.estimation.proposal.Modification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class CcpDeletionModificationBuilderTest {
    private ChainSegment replacement;

    @BeforeMethod
    public void setUp(){
        replacement = mock(ChainSegment.class);
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildBeforeSettingAnyValues() throws Exception {
        CcpDeletionModificationBuilder builder = new CcpDeletionModificationBuilder();
        builder.with().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You cannot set properties for an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingStartIdentity() throws Exception {
        CcpDeletionModificationBuilder builder = new CcpDeletionModificationBuilder();
        builder.with().start(5).identity().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You cannot set properties for an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingEndIdentity() throws Exception {
        CcpDeletionModificationBuilder builder = new CcpDeletionModificationBuilder();
        builder.with().end(5).identity().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You cannot set properties for an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingProbabilityIdentity() throws Exception {
        CcpDeletionModificationBuilder builder = new CcpDeletionModificationBuilder();
        builder.with().logAlterSelectionProbability(5).identity().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You cannot set properties for an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingReplancementIdentity() throws Exception {
        CcpDeletionModificationBuilder builder = new CcpDeletionModificationBuilder();
        builder.with().replacement(replacement).identity().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingProbabilityOnly() throws Exception {
        CcpDeletionModificationBuilder builder = new CcpDeletionModificationBuilder();
        builder.with().logAlterSelectionProbability(5).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingStartOnly() throws Exception {
        CcpDeletionModificationBuilder builder = new CcpDeletionModificationBuilder();
        builder.with().start(5).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingReplacementOnly() throws Exception {
        CcpDeletionModificationBuilder builder = new CcpDeletionModificationBuilder();
        builder.with().replacement(replacement).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingEndOnly() throws Exception {
        CcpDeletionModificationBuilder builder = new CcpDeletionModificationBuilder();
        builder.with().end(5).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingStartEndOnly() throws Exception {
        CcpDeletionModificationBuilder builder = new CcpDeletionModificationBuilder();
        builder.with().start(5).end(10).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingStartEndReplacementOnly() throws Exception {
        CcpDeletionModificationBuilder builder = new CcpDeletionModificationBuilder();
        builder.with().start(5).end(10).replacement(replacement).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingStartEndProbOnly() throws Exception {
        CcpDeletionModificationBuilder builder = new CcpDeletionModificationBuilder();
        builder.with().start(5).end(10).logAlterSelectionProbability(-.123).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildReplacementProbOnly() throws Exception {
        CcpDeletionModificationBuilder builder = new CcpDeletionModificationBuilder();
        builder.with().replacement(replacement).logAlterSelectionProbability(-.123).build();
    }

    public void deletion() throws Exception {
        CcpDeletionModificationBuilder builder = new CcpDeletionModificationBuilder();
        Modification result = builder.with().start(5).end(10).logAlterSelectionProbability(-20).replacement(replacement).build();
        assertThat(result).isOfAnyClassIn(CcpDeletionModification.class);
        assertThat(((CcpDeletionModification)result).getStart()).isEqualTo(5);
        assertThat(((CcpDeletionModification)result).getEnd()).isEqualTo(10);
        assertThat(((CcpDeletionModification)result).getLogAlterSelectionProbability()).isEqualTo(-20);
        assertThat(((CcpDeletionModification)result).getReplacement()).isEqualTo(replacement);
        assertThat(result.isIdentity()).isFalse();
    }

        public void testIdentity() throws Exception {
        CcpDeletionModificationBuilder builder = new CcpDeletionModificationBuilder();
        Modification result = builder.with().identity().build();
        assertThat(result.isIdentity()).isTrue();
    }
}
