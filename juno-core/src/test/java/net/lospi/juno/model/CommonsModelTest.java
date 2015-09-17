package net.lospi.juno.model;

import com.google.common.collect.ImmutableList;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class CommonsModelTest {
    private List rateEffects, alterSelectionEffects, allEffects;
    private ObjectVector allEffectsParameter;

    @BeforeMethod
    public void setUp() {
        rateEffects = ImmutableList.of(mock(RateEffect.class), mock(RateEffect.class));
        alterSelectionEffects = ImmutableList.of(mock(AlterSelectionEffect.class), mock(AlterSelectionEffect.class), mock(AlterSelectionEffect.class));
        allEffects = new ArrayList(rateEffects);
        allEffects.addAll(alterSelectionEffects);

        allEffectsParameter = new SafeObjectVector(new double[] { -1D, -2D, 1D, 2D, 3D }, allEffects);
    }

    public void getAllEffectsSize() {
        CommonsModel underStudy = new CommonsModel(rateEffects, alterSelectionEffects, allEffectsParameter);
        int result = underStudy.getAllEffectsSize();
        assertThat(result).isEqualTo(5);
    }

    public void globalRate() {
        CommonsModel underStudy = new CommonsModel(rateEffects, alterSelectionEffects, allEffectsParameter);
        double result = underStudy.globalRate();
        assertThat(result).isEqualTo(-1D);
    }

    public void getAllEffects() {
        CommonsModel underStudy = new CommonsModel(rateEffects, alterSelectionEffects, allEffectsParameter);
        List result = underStudy.getAllEffects();
        assertThat(result).containsExactly(rateEffects.get(0), rateEffects.get(1),
                alterSelectionEffects.get(0), alterSelectionEffects.get(1), alterSelectionEffects.get(2));
    }

    public void getAllAlterEffectsSize() {
        CommonsModel underStudy = new CommonsModel(rateEffects, alterSelectionEffects, allEffectsParameter);
        int result = underStudy.getAllAlterEffectsSize();
        assertThat(result).isEqualTo(3);
    }

    public void getAlterSelectionEffects() {
        CommonsModel underStudy = new CommonsModel(rateEffects, alterSelectionEffects, allEffectsParameter);
        List result = underStudy.getAlterSelectionEffects();
        assertThat(result).isEqualTo(alterSelectionEffects);
    }

    public void getRateEffectsParameter() {
        CommonsModel underStudy = new CommonsModel(rateEffects, alterSelectionEffects, allEffectsParameter);
        ObjectVector result = underStudy.getRateEffectsParameter();
        assertThat(result.getDimension()).isEqualTo(2);
        assertThat(result.getEntry(0)).isEqualTo(-1d);
        assertThat(result.getEntry(1)).isEqualTo(-2d);
    }

    public void getRateEffects() {
        CommonsModel underStudy = new CommonsModel(rateEffects, alterSelectionEffects, allEffectsParameter);
        List result = underStudy.getRateEffects();
        assertThat(result).isEqualTo(rateEffects);
    }

    public void getAllRateEffectsSize() {
        CommonsModel underStudy = new CommonsModel(rateEffects, alterSelectionEffects, allEffectsParameter);
        int result = underStudy.getAllRateEffectsSize();
        assertThat(result).isEqualTo(2);
    }

    public void getAlterSelectionEffectsParameter() {
        CommonsModel underStudy = new CommonsModel(rateEffects, alterSelectionEffects, allEffectsParameter);
        ObjectVector result = underStudy.getAlterSelectionEffectsParameter();
        assertThat(result.getDimension()).isEqualTo(3);
        assertThat(result.getEntry(0)).isEqualTo(1d);
        assertThat(result.getEntry(1)).isEqualTo(2d);
        assertThat(result.getEntry(2)).isEqualTo(3d);
    }

    public void canToString() {
        CommonsModel underStudy = new CommonsModel(rateEffects, alterSelectionEffects, allEffectsParameter);
        String result = underStudy.toString();
        assertThat(result).isNotNull();
    }

    public void getAllEffectsParameter() {
        CommonsModel underStudy = new CommonsModel(rateEffects, alterSelectionEffects, allEffectsParameter);
        ObjectVector result = underStudy.getAllEffectsParameter();
        assertThat(result).isSameAs(allEffectsParameter);
    }
}
