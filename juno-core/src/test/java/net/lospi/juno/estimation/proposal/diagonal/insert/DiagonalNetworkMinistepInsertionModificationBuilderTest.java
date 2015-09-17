/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.insert;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.proposal.Modification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class DiagonalNetworkMinistepInsertionModificationBuilderTest {
    private LikelihoodDerivatives derivatives;

    @BeforeMethod
    public void setUp() {
        derivatives = mock(LikelihoodDerivatives.class);
        when(derivatives.getLogLikelihood()).thenReturn(-1.234);
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either all properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildBeforeSettingAnyValues() throws Exception {
        DiagonalNetworkMinistepInsertionModificationBuilder builder = new DiagonalNetworkMinistepInsertionModificationBuilder();
        builder.with().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You cannot set properties for an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingIndexAndIdentity() throws Exception {
        DiagonalNetworkMinistepInsertionModificationBuilder builder = new DiagonalNetworkMinistepInsertionModificationBuilder();
        builder.with().index(5).identity().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You cannot set properties for an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingActorAspectAndIdentity() throws Exception {
        ActorAspect actorAspect = mock(ActorAspect.class);
        DiagonalNetworkMinistepInsertionModificationBuilder builder = new DiagonalNetworkMinistepInsertionModificationBuilder();
        builder.with().actorAspect(actorAspect).identity().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You cannot set properties for an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingProbabilityAndIdentity() throws Exception {
        DiagonalNetworkMinistepInsertionModificationBuilder builder = new DiagonalNetworkMinistepInsertionModificationBuilder();
        builder.with().linkLikelihoodDerivatives(derivatives).identity().build();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void buildSettingMinistepInsertionIndexAndIdentity() throws Exception {
        DiagonalNetworkMinistepInsertionModificationBuilder builder = new DiagonalNetworkMinistepInsertionModificationBuilder();
        builder.with().actorAspect(mock(ActorAspect.class)).index(10).build();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void buildSettingActorAspect() throws Exception {
        ActorAspect actorAspect = mock(ActorAspect.class);
        DiagonalNetworkMinistepInsertionModificationBuilder builder = new DiagonalNetworkMinistepInsertionModificationBuilder();
        builder.with().actorAspect(actorAspect).build();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void buildSettingInsertionIndex() throws Exception {
        DiagonalNetworkMinistepInsertionModificationBuilder builder = new DiagonalNetworkMinistepInsertionModificationBuilder();
        builder.with().index(5).build();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void buildSettingActorAspectIndex() throws Exception {
        ActorAspect actorAspect = mock(ActorAspect.class);
        DiagonalNetworkMinistepInsertionModificationBuilder builder = new DiagonalNetworkMinistepInsertionModificationBuilder();
        builder.with().actorAspect(actorAspect).build();
    }

    public void insertion() throws Exception {
        ActorAspect actorAspect = mock(ActorAspect.class);
        when(actorAspect.getActor()).thenReturn("alter");
        DiagonalNetworkMinistepInsertionModificationBuilder builder = new DiagonalNetworkMinistepInsertionModificationBuilder();
        Modification result = builder.with()
                .actorAspect(actorAspect)
                .index(5)
                .linkLikelihoodDerivatives(derivatives)
                .build();
        assertThat(result).isOfAnyClassIn(DiagonalNetworkMinistepInsertionModification.class);
        assertThat(((DiagonalNetworkMinistepInsertionModification)result).getInsertionIndex()).isEqualTo(5);
        assertThat(((DiagonalNetworkMinistepInsertionModification)result).getMinistep().getActorAspect()).isEqualTo(actorAspect);
        assertThat(((DiagonalNetworkMinistepInsertionModification)result).getMinistep().getAlter()).isEqualTo(actorAspect.getActor());
        assertThat(((DiagonalNetworkMinistepInsertionModification)result).getMinistep().getLikelihoodDerivatives()).isEqualTo(derivatives);
        assertThat(((DiagonalNetworkMinistepInsertionModification)result).getLogAlterSelectionProbability()).isEqualTo(-1.234);
        assertThat(result.isIdentity()).isFalse();
    }

    public void testIdentity() throws Exception {
        DiagonalNetworkMinistepInsertionModificationBuilder builder = new DiagonalNetworkMinistepInsertionModificationBuilder();
        Modification result = builder.with().identity().build();
        assertThat(result.isIdentity()).isTrue();
    }
}
