package net.lospi.juno.estimation.ml;

import net.lospi.juno.estimation.calc.LikelihoodDerivativesCalculator;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.model.ObjectMatrix;
import net.lospi.juno.model.SafeObjectMatrix;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class SerialWeightMatrixCalculatorTest {
    private List<LikelihoodDerivatives> likelihoodDerivatives;
    private LikelihoodDerivativesCalculator likelihoodDerivativesCalculator;
    private LikelihoodDerivatives completeDataDerivatives;
    private List effects;
    private ObjectMatrix information;

    @BeforeMethod
    public void setUp() {
        completeDataDerivatives = mock(LikelihoodDerivatives.class);
        likelihoodDerivativesCalculator = mock(LikelihoodDerivativesCalculator.class);
        likelihoodDerivatives = mock(List.class);
        effects = mock(List.class);
        when(effects.size()).thenReturn(2);
        information = new SafeObjectMatrix(new double[][]{
                new double[] { -1D, -2D },
                new double[] { -3D, -4D }
        }, effects, effects);

        when(likelihoodDerivativesCalculator.calculate(likelihoodDerivatives, effects)).thenReturn(completeDataDerivatives);
        when(completeDataDerivatives.getInformation()).thenReturn(information);
    }

    public void calculate() {
        SerialWeightMatrixCalculator underStudy = new SerialWeightMatrixCalculator(likelihoodDerivativesCalculator);
        WeightMatrix result = underStudy.calculate(likelihoodDerivatives, effects);
        assertThat(result.getWeights().getEntry(0,0)).isEqualTo(1.0);
        assertThat(result.getWeights().getEntry(1,0)).isEqualTo(0.0);
        assertThat(result.getWeights().getEntry(0,1)).isEqualTo(0.0);
        assertThat(result.getWeights().getEntry(1,1)).isEqualTo(0.25);
    }
}
