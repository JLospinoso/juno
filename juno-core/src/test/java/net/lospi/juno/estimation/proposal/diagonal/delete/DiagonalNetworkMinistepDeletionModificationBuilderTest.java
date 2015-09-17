/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.delete;

import net.lospi.juno.estimation.proposal.Modification;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

@Test(groups="unit")
public class DiagonalNetworkMinistepDeletionModificationBuilderTest {
    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildBeforeSettingAnyValues() throws Exception {
        DiagonalNetworkMinistepDeletionModificationBuilder builder = new DiagonalNetworkMinistepDeletionModificationBuilder();
        builder.with().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You cannot set properties for an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingIndexIdentity() throws Exception {
        DiagonalNetworkMinistepDeletionModificationBuilder builder = new DiagonalNetworkMinistepDeletionModificationBuilder();
        builder.with().index(5).identity().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You cannot set properties for an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingProbabilityIdentity() throws Exception {
        DiagonalNetworkMinistepDeletionModificationBuilder builder = new DiagonalNetworkMinistepDeletionModificationBuilder();
        builder.with().logAlterSelectionProbability(5).identity().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingProbabilityOnly() throws Exception {
        DiagonalNetworkMinistepDeletionModificationBuilder builder = new DiagonalNetworkMinistepDeletionModificationBuilder();
        builder.with().logAlterSelectionProbability(5).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingIndexOnly() throws Exception {
        DiagonalNetworkMinistepDeletionModificationBuilder builder = new DiagonalNetworkMinistepDeletionModificationBuilder();
        builder.with().index(5).build();
    }

    public void deletion() throws Exception {
        DiagonalNetworkMinistepDeletionModificationBuilder builder = new DiagonalNetworkMinistepDeletionModificationBuilder();
        Modification result = builder.with().index(5).logAlterSelectionProbability(-20).build();
        assertThat(result).isOfAnyClassIn(DiagonalNetworkMinistepDeletionModification.class);
        assertThat(((DiagonalNetworkMinistepDeletionModification)result).getDeletionIndex()).isEqualTo(5);
        assertThat(((DiagonalNetworkMinistepDeletionModification)result).getLogAlterSelectionProbability()).isEqualTo(-20);
        assertThat(result.isIdentity()).isFalse();
    }

        public void testIdentity() throws Exception {
        DiagonalNetworkMinistepDeletionModificationBuilder builder = new DiagonalNetworkMinistepDeletionModificationBuilder();
        Modification result = builder.with().identity().build();
        assertThat(result.isIdentity()).isTrue();
    }
}
