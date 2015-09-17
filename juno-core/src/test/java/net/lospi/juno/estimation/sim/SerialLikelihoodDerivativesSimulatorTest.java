package net.lospi.juno.estimation.sim;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.calc.LikelihoodDerivativesCalculator;
import net.lospi.juno.estimation.calc.RateLikelihoodDerivativesCalculator;
import net.lospi.juno.estimation.calc.SimpleChainLikelihoodUpdater;
import net.lospi.juno.estimation.elements.CompositeLikelihoodDerivativesBuilder;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.model.AlterSelectionEffect;
import net.lospi.juno.model.Model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Test(groups="unit")
public class SerialLikelihoodDerivativesSimulatorTest {
    private Model model;
    private LikelihoodDerivativesCalculator likelihoodDerivativesCalculator;
    private int phaseThreeIterations;
    private ChainSimulator chainSimulator;
    private Chain initialChain, chain1, chain2, chain3;
    private LikelihoodDerivatives alterLikelihoodDerivatives1, alterLikelihoodDerivatives2, alterLikelihoodDerivatives3,
            rateLikelihoodDerivatives1, rateLikelihoodDerivatives2, rateLikelihoodDerivatives3,
            compositeDerivatives1, compositeDerivatives2, compositeDerivatives3;
    private List<LikelihoodDerivatives> linkDerivatives1, linkDerivatives2, linkDerivatives3;
    private SimpleChainLikelihoodUpdater simpleChainLikelihoodUpdater;
    private List<AlterSelectionEffect> effects;
    private CompositeLikelihoodDerivativesBuilder compositeLikelihoodDerivativesBuilder;
    private RateLikelihoodDerivativesCalculator rateLikelihoodDerivativesCalculator;
    private CompositeLikelihoodDerivativesBuilder.Stub stub;

    @BeforeMethod
    public void setUp(){
        stub = mock(CompositeLikelihoodDerivativesBuilder.Stub.class);
        effects = mock(List.class);
        model = mock(Model.class);
        likelihoodDerivativesCalculator = mock(LikelihoodDerivativesCalculator.class);
        chainSimulator = mock(ChainSimulator.class);
        phaseThreeIterations = 3;
        initialChain = mock(Chain.class);
        chain1 = mock(Chain.class);
        chain2 = mock(Chain.class);
        chain3 = mock(Chain.class);
        alterLikelihoodDerivatives1 = mock(LikelihoodDerivatives.class);
        alterLikelihoodDerivatives2 = mock(LikelihoodDerivatives.class);
        alterLikelihoodDerivatives3 = mock(LikelihoodDerivatives.class);
        rateLikelihoodDerivatives1 = mock(LikelihoodDerivatives.class);
        rateLikelihoodDerivatives2 = mock(LikelihoodDerivatives.class);
        rateLikelihoodDerivatives3 = mock(LikelihoodDerivatives.class);
        compositeDerivatives1 = mock(LikelihoodDerivatives.class);
        compositeDerivatives2 = mock(LikelihoodDerivatives.class);
        compositeDerivatives3 = mock(LikelihoodDerivatives.class);
        linkDerivatives1 = mock(List.class);
        linkDerivatives2 = mock(List.class);
        linkDerivatives3 = mock(List.class);
        simpleChainLikelihoodUpdater = mock(SimpleChainLikelihoodUpdater.class);
        compositeLikelihoodDerivativesBuilder = mock(CompositeLikelihoodDerivativesBuilder.class);
        rateLikelihoodDerivativesCalculator = mock(RateLikelihoodDerivativesCalculator.class);

        when(chainSimulator.getChain())
                .thenReturn(chain1)
                .thenReturn(chain2)
                .thenReturn(chain3);
        when(chain1.getLinkLikelihoodDerivatives()).thenReturn(linkDerivatives1);
        when(chain2.getLinkLikelihoodDerivatives()).thenReturn(linkDerivatives2);
        when(chain3.getLinkLikelihoodDerivatives()).thenReturn(linkDerivatives3);
        when(model.getAlterSelectionEffects()).thenReturn(effects);
        when(likelihoodDerivativesCalculator.calculate(linkDerivatives1, effects)).thenReturn(alterLikelihoodDerivatives1);
        when(likelihoodDerivativesCalculator.calculate(linkDerivatives2, effects)).thenReturn(alterLikelihoodDerivatives2);
        when(likelihoodDerivativesCalculator.calculate(linkDerivatives3, effects)).thenReturn(alterLikelihoodDerivatives3);
        when(chainSimulator.isChainSet()).thenReturn(true);
        when(rateLikelihoodDerivativesCalculator.calculate(chain1, model)).thenReturn(rateLikelihoodDerivatives1);
        when(rateLikelihoodDerivativesCalculator.calculate(chain2, model)).thenReturn(rateLikelihoodDerivatives2);
        when(rateLikelihoodDerivativesCalculator.calculate(chain3,model)).thenReturn(rateLikelihoodDerivatives3);
        when(compositeLikelihoodDerivativesBuilder.with()).thenReturn(stub);
        when(stub.derivatives(any(LikelihoodDerivatives.class))).thenReturn(stub);
        when(stub.build()).thenReturn(compositeDerivatives1)
                .thenReturn(compositeDerivatives2)
                .thenReturn(compositeDerivatives3);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void simulateLikelihoodDerivativesThrowsWhenChainNotSet() throws Exception {
        when(chainSimulator.isChainSet()).thenReturn(false);
        SerialLikelihoodDerivativesSimulator simulator = new SerialLikelihoodDerivativesSimulator(chainSimulator, simpleChainLikelihoodUpdater, likelihoodDerivativesCalculator, compositeLikelihoodDerivativesBuilder, rateLikelihoodDerivativesCalculator);
        simulator.simulateLikelihoodDerivatives(model, phaseThreeIterations);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void burnInThrowsWhenChainNotSet() throws Exception {
        when(chainSimulator.isChainSet()).thenReturn(false);
        SerialLikelihoodDerivativesSimulator simulator = new SerialLikelihoodDerivativesSimulator(chainSimulator, simpleChainLikelihoodUpdater, likelihoodDerivativesCalculator, compositeLikelihoodDerivativesBuilder, rateLikelihoodDerivativesCalculator);
        simulator.burnIn(model, 100);
    }

    public void interval0() throws Exception {
        SerialLikelihoodDerivativesSimulator simulator = new SerialLikelihoodDerivativesSimulator(chainSimulator, simpleChainLikelihoodUpdater, likelihoodDerivativesCalculator, compositeLikelihoodDerivativesBuilder, rateLikelihoodDerivativesCalculator);
        simulator.setChain(initialChain);

        List<LikelihoodDerivatives> results = simulator.simulateLikelihoodDerivatives(model, phaseThreeIterations);
        assertThat(results).containsExactly(compositeDerivatives1, compositeDerivatives2, compositeDerivatives3);

        verify(chainSimulator).setModel(model);
        verify(chainSimulator, times(3)).advance();
        verify(chainSimulator, times(3)).getChain();
        verify(stub).derivatives(alterLikelihoodDerivatives1);
        verify(stub).derivatives(rateLikelihoodDerivatives1);
        verify(stub).derivatives(alterLikelihoodDerivatives2);
        verify(stub).derivatives(rateLikelihoodDerivatives2);
        verify(stub).derivatives(alterLikelihoodDerivatives3);
        verify(stub).derivatives(rateLikelihoodDerivatives3);
    }

    public void interval1() throws Exception {
        SerialLikelihoodDerivativesSimulator simulator = new SerialLikelihoodDerivativesSimulator(chainSimulator, simpleChainLikelihoodUpdater, likelihoodDerivativesCalculator, compositeLikelihoodDerivativesBuilder, rateLikelihoodDerivativesCalculator);
        simulator.setSamplingInterval(1);

        List<LikelihoodDerivatives> results = simulator.simulateLikelihoodDerivatives(model, phaseThreeIterations);
        assertThat(results).containsExactly(compositeDerivatives1, compositeDerivatives2, compositeDerivatives3);

        verify(chainSimulator).setModel(model);
        verify(chainSimulator, times(6)).advance();
        verify(chainSimulator, times(3)).getChain();
        verify(stub).derivatives(alterLikelihoodDerivatives1);
        verify(stub).derivatives(rateLikelihoodDerivatives1);
        verify(stub).derivatives(alterLikelihoodDerivatives2);
        verify(stub).derivatives(rateLikelihoodDerivatives2);
        verify(stub).derivatives(alterLikelihoodDerivatives3);
        verify(stub).derivatives(rateLikelihoodDerivatives3);
    }

    public void interval3() throws Exception {
        SerialLikelihoodDerivativesSimulator simulator = new SerialLikelihoodDerivativesSimulator(chainSimulator, simpleChainLikelihoodUpdater, likelihoodDerivativesCalculator, compositeLikelihoodDerivativesBuilder, rateLikelihoodDerivativesCalculator);
        simulator.setSamplingInterval(3);

        List<LikelihoodDerivatives> results = simulator.simulateLikelihoodDerivatives(model, phaseThreeIterations);
        assertThat(results).containsExactly(compositeDerivatives1, compositeDerivatives2, compositeDerivatives3);

        verify(chainSimulator).setModel(model);
        verify(chainSimulator, times(12)).advance();
        verify(chainSimulator, times(3)).getChain();
        verify(stub).derivatives(alterLikelihoodDerivatives1);
        verify(stub).derivatives(rateLikelihoodDerivatives1);
        verify(stub).derivatives(alterLikelihoodDerivatives2);
        verify(stub).derivatives(rateLikelihoodDerivatives2);
        verify(stub).derivatives(alterLikelihoodDerivatives3);
        verify(stub).derivatives(rateLikelihoodDerivatives3);
    }

    public void canBurnIn() {
        int burnIterations = 100;
        SerialLikelihoodDerivativesSimulator simulator = new SerialLikelihoodDerivativesSimulator(chainSimulator, simpleChainLikelihoodUpdater, likelihoodDerivativesCalculator, compositeLikelihoodDerivativesBuilder, rateLikelihoodDerivativesCalculator);
        simulator.setChain(initialChain);
        simulator.burnIn(model, burnIterations);
        verify(chainSimulator).setModel(model);
        verify(chainSimulator, times(burnIterations)).advance();
    }
}
