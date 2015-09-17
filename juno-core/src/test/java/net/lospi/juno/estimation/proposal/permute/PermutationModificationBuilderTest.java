package net.lospi.juno.estimation.proposal.permute;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.estimation.proposal.ChainSegment;
import net.lospi.juno.estimation.proposal.Modification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class PermutationModificationBuilderTest {
    private ChainSegment replacement;

    @BeforeMethod
    public void setUp(){
        replacement = mock(ChainSegment.class);
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildBeforeSettingAnyValues() throws Exception {
        PermutationModificationBuilder builder = new PermutationModificationBuilder();
        builder.with().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You cannot set properties for an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingStartIdentity() throws Exception {
        PermutationModificationBuilder builder = new PermutationModificationBuilder();
        builder.with().start(5).identity().build();
    }


    @Test(expectedExceptionsMessageRegExp = "You cannot set properties for an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingEndIdentity() throws Exception {
        PermutationModificationBuilder builder = new PermutationModificationBuilder();
        builder.with().end(5).identity().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You cannot set properties for an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingProbabilityIdentity() throws Exception {
        PermutationModificationBuilder builder = new PermutationModificationBuilder();
        builder.with().logAlterSelectionProbability(5).identity().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You cannot set properties for an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingReplancementIdentity() throws Exception {
        PermutationModificationBuilder builder = new PermutationModificationBuilder();
        builder.with().replacement(replacement).identity().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingProbabilityOnly() throws Exception {
        PermutationModificationBuilder builder = new PermutationModificationBuilder();
        builder.with().logAlterSelectionProbability(5).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingStartOnly() throws Exception {
        PermutationModificationBuilder builder = new PermutationModificationBuilder();
        builder.with().start(5).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingReplacementOnly() throws Exception {
        PermutationModificationBuilder builder = new PermutationModificationBuilder();
        builder.with().replacement(replacement).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingEndOnly() throws Exception {
        PermutationModificationBuilder builder = new PermutationModificationBuilder();
        builder.with().end(5).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingMissingAlterSelection() throws Exception {
        PermutationModificationBuilder builder = new PermutationModificationBuilder();
        builder.with().start(3).end(5).replacement(replacement).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set either properties or an identity modification.", expectedExceptions = IllegalStateException.class)
    public void buildSettingMissingReplacement() throws Exception {
        PermutationModificationBuilder builder = new PermutationModificationBuilder();
        builder.with().start(3).end(5).logAlterSelectionProbability(-3D).build();
    }

    public void build() throws Exception {
        PermutationModificationBuilder builder = new PermutationModificationBuilder();
        Modification result = builder.with().start(5).end(10).logAlterSelectionProbability(-20).replacement(replacement).build();
        assertThat(result).isOfAnyClassIn(PermutationModification.class);
        assertThat(((PermutationModification)result).getStart()).isEqualTo(5);
        assertThat(((PermutationModification)result).getEnd()).isEqualTo(10);
        assertThat(((PermutationModification)result).getLogAlterSelectionProbability()).isEqualTo(-20);
        assertThat(((PermutationModification)result).getReplacement()).isEqualTo(replacement);
        assertThat(result.isIdentity()).isFalse();
    }

        public void testIdentity() throws Exception {
        PermutationModificationBuilder builder = new PermutationModificationBuilder();
        Modification result = builder.with().identity().build();
        assertThat(result.isIdentity()).isTrue();
    }
}