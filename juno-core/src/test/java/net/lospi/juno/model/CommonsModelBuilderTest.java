package net.lospi.juno.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class CommonsModelBuilderTest {
    private AlterSelectionEffect effect1, effect2;
    private Map<AlterSelectionEffect, Double> expectedEffectParameters;
    private Model existingModel;
    private ObjectVector newParameter;
    private List<RateEffect> rateEffects;
    private List<AlterSelectionEffect> alterSelectionEffects;
    private static final double ALTER_PARAMETER_1 = 1D, ALTER_PARAMETER_2 = 2D,
            RATE_PARAMETER_1 = 3D, RATE_PARAMETER_2 = 4D;

    private Map<AlterSelectionEffect, Double> expectedAlterParameter;
    private List<Effect> allEffects;

    @BeforeMethod
    public void setUp() {
        rateEffects = ImmutableList.of(mock(RateEffect.class),mock(RateEffect.class));
        alterSelectionEffects = ImmutableList.of(mock(AlterSelectionEffect.class), mock(AlterSelectionEffect.class), mock(AlterSelectionEffect.class));
        existingModel = mock(Model.class);
        effect1 = mock(AlterSelectionEffect.class);
        effect2 = mock(AlterSelectionEffect.class);
        expectedEffectParameters = ImmutableMap.of(effect1, ALTER_PARAMETER_1, effect2, ALTER_PARAMETER_2);
        allEffects = new ArrayList<Effect>(rateEffects);
        allEffects.addAll(alterSelectionEffects);
        newParameter = mock(ObjectVector.class);

        when(existingModel.getAlterSelectionEffects()).thenReturn(alterSelectionEffects);
        when(existingModel.getRateEffects()).thenReturn(rateEffects);
        expectedAlterParameter = ImmutableMap.of(effect1, ALTER_PARAMETER_1, effect2, ALTER_PARAMETER_2);
    }

    public void canBuildRateOnly() throws Exception {
        CommonsModelBuilder builder = new CommonsModelBuilder();
        Model result = builder.newModel().withGlobalRate(RATE_PARAMETER_1).build();
        assertThat(result.getRateEffects().size()).isEqualTo(1);
        assertThat(result.getRateEffectsParameter().getEntry(0)).isEqualTo(RATE_PARAMETER_1);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void buildNotSettingGlobalRate() throws Exception {
        CommonsModelBuilder builder = new CommonsModelBuilder();
        builder.newModel().withEffect(effect1, ALTER_PARAMETER_1).build();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void throwsWhenNewModelAndCopy() throws Exception {
        CommonsModelBuilder builder = new CommonsModelBuilder();
        builder.newModel()
                .withEffect(effect1, expectedEffectParameters.get(effect1))
                .withEffect(effect2, expectedEffectParameters.get(effect2))
                .withGlobalRate(RATE_PARAMETER_1)
                .from(existingModel)
                .withNewParameter(newParameter)
                .build();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void throwsWhenNoParameterGivenForCopy() throws Exception {
        CommonsModelBuilder builder = new CommonsModelBuilder();
        builder.newModel()
                .from(existingModel)
                .build();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void throwsWhenNoModelGivenForCopy() throws Exception {
        CommonsModelBuilder builder = new CommonsModelBuilder();
        builder.newModel()
                .withNewParameter(newParameter)
                .build();
    }

    public void copiesCorrectly() throws Exception {
        CommonsModelBuilder builder = new CommonsModelBuilder();
        Model newModel = builder.newModel()
                .from(existingModel)
                .withNewParameter(newParameter)
                .build();

        assertThat(newModel.getRateEffects()).isEqualTo(rateEffects);
        assertThat(newModel.getAlterSelectionEffects()).isEqualTo(alterSelectionEffects);
        assertThat(newModel.getAllEffectsParameter()).isSameAs(newParameter);
    }

    public void buildsRateCorrectly() throws Exception {
        CommonsModelBuilder builder = new CommonsModelBuilder();
        Model model = builder.newModel()
                .withEffect(effect1, expectedEffectParameters.get(effect1))
                .withEffect(effect2, expectedEffectParameters.get(effect2))
                .withGlobalRate(RATE_PARAMETER_1)
                .build();

        assertThat(model.globalRate()).isEqualTo(3D);
    }

    public void buildsParameterCorrectly() throws Exception {
        CommonsModelBuilder builder = new CommonsModelBuilder();
        Model model = builder.newModel()
                .withEffect(effect1, expectedEffectParameters.get(effect1))
                .withEffect(effect2, expectedEffectParameters.get(effect2))
                .withGlobalRate(RATE_PARAMETER_1)
                .build();

        assertThat(model.getRateEffectsParameter().getDimension()).isEqualTo(1);
        assertThat(model.getAlterSelectionEffectsParameter().getDimension()).isEqualTo(2);
        assertThat(model.getAllEffectsParameter().getDimension()).isEqualTo(3);

        assertThat(model.getRateEffectsParameter().getEntry(0)).isEqualTo(RATE_PARAMETER_1);
        for(int i=0; i<2; i++) {
            AlterSelectionEffect effect = model.getAlterSelectionEffects().get(i);
            assertThat(model.getAlterSelectionEffectsParameter().getEntry(i)).isEqualTo(expectedAlterParameter.get(effect));
        }
    }

    public void canClobberRate() throws Exception {
        CommonsModelBuilder builder = new CommonsModelBuilder();
        Model model = builder.newModel()
                .withEffect(effect1, expectedEffectParameters.get(effect1))
                .withEffect(effect2, expectedEffectParameters.get(effect2))
                .withGlobalRate(RATE_PARAMETER_1)
                .withGlobalRate(RATE_PARAMETER_2)
                .build();

        assertThat(model.globalRate()).isEqualTo(RATE_PARAMETER_2);
    }
}