/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.insert;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.estimation.proposal.ChainSegment;
import net.lospi.juno.estimation.proposal.Modification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class CcpInsertionModificationBuilderTest {
    private ChainSegment replacement;
    private ActorAspect actorAspect;

    @BeforeMethod
    public void setUp(){
        replacement = mock(ChainSegment.class);
        actorAspect = mock(ActorAspect.class);
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildBeforeSettingAnyValues() throws Exception {
        CcpInsertionModificationBuilder builder = new CcpInsertionModificationBuilder();
        builder.with().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You cannot set properties for an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingStartIdentity() throws Exception {
        CcpInsertionModificationBuilder builder = new CcpInsertionModificationBuilder();
        builder.with().start(5).identity().build();
    }


    @Test(expectedExceptionsMessageRegExp = "You cannot set properties for an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingEndIdentity() throws Exception {
        CcpInsertionModificationBuilder builder = new CcpInsertionModificationBuilder();
        builder.with().end(5).identity().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You cannot set properties for an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingEndActorAspect() throws Exception {
        CcpInsertionModificationBuilder builder = new CcpInsertionModificationBuilder();
        builder.with().egoAspect(actorAspect).identity().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You cannot set properties for an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingProbabilityIdentity() throws Exception {
        CcpInsertionModificationBuilder builder = new CcpInsertionModificationBuilder();
        builder.with().logAlterSelectionProbability(5).identity().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You cannot set properties for an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingReplancementIdentity() throws Exception {
        CcpInsertionModificationBuilder builder = new CcpInsertionModificationBuilder();
        builder.with().replacement(replacement).identity().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingProbabilityOnly() throws Exception {
        CcpInsertionModificationBuilder builder = new CcpInsertionModificationBuilder();
        builder.with().logAlterSelectionProbability(5).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingStartOnly() throws Exception {
        CcpInsertionModificationBuilder builder = new CcpInsertionModificationBuilder();
        builder.with().start(5).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingReplacementOnly() throws Exception {
        CcpInsertionModificationBuilder builder = new CcpInsertionModificationBuilder();
        builder.with().replacement(replacement).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingEndOnly() throws Exception {
        CcpInsertionModificationBuilder builder = new CcpInsertionModificationBuilder();
        builder.with().end(5).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingNoProbability() throws Exception {
        CcpInsertionModificationBuilder builder = new CcpInsertionModificationBuilder();
        builder.with().start(3).end(5).replacement(replacement).egoAspect(actorAspect).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingNoReplacement() throws Exception {
        CcpInsertionModificationBuilder builder = new CcpInsertionModificationBuilder();
        builder.with().start(3).end(5).logAlterSelectionProbability(-2D).egoAspect(actorAspect).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingNoEgoAspect() throws Exception {
        CcpInsertionModificationBuilder builder = new CcpInsertionModificationBuilder();
        builder.with().start(3).end(5).logAlterSelectionProbability(-2D).replacement(replacement).build();
    }

    public void deletion() throws Exception {
        CcpInsertionModificationBuilder builder = new CcpInsertionModificationBuilder();
        Modification result = builder.with().start(5).end(10).egoAspect(actorAspect).logAlterSelectionProbability(-20).replacement(replacement).build();
        assertThat(result).isOfAnyClassIn(CcpInsertionModification.class);
        assertThat(((CcpInsertionModification)result).getStart()).isEqualTo(5);
        assertThat(((CcpInsertionModification)result).getEnd()).isEqualTo(10);
        assertThat(((CcpInsertionModification)result).getLogAlterSelectionProbability()).isEqualTo(-20);
        assertThat(((CcpInsertionModification)result).getReplacement()).isEqualTo(replacement);
        assertThat(((CcpInsertionModification)result).getActorAspect()).isEqualTo(actorAspect);
        assertThat(result.isIdentity()).isFalse();
    }

        public void testIdentity() throws Exception {
        CcpInsertionModificationBuilder builder = new CcpInsertionModificationBuilder();
        Modification result = builder.with().identity().build();
        assertThat(result.isIdentity()).isTrue();
    }
}