package net.lospi.juno.estimation.calc;

import net.lospi.juno.elements.*;
import net.lospi.juno.model.Model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

@Test(groups="unit")
public class SimpleChainLikelihoodUpdaterTest {
    private LinkLikelihoodDerivativesCalculator<Ministep> calculator;
    private Chain chain;
    private Model model;
    private Ministep link1, link2;
    private State state1, state2;

    @BeforeMethod
    public void setUp() {
        calculator = mock(LinkLikelihoodDerivativesCalculator.class);
        chain = mock(Chain.class);
        model = mock(Model.class);
        link1 = mock(Ministep.class);
        link2 = mock(Ministep.class);
        state1 = mock(State.class);
        state2 = mock(State.class);
    }

    public void updateLikelihoodsZeroLength() throws Exception {
        SimpleChainLikelihoodUpdater updater = new SimpleChainLikelihoodUpdater(calculator);
        when(chain.getSize()).thenReturn(0);
        updater.updateLikelihoods(chain, model);
        verifyZeroInteractions(calculator);
    }

    public void updateScoresZeroLength() throws Exception {
        SimpleChainLikelihoodUpdater updater = new SimpleChainLikelihoodUpdater(calculator);
        when(chain.getSize()).thenReturn(0);
        updater.updateScores(chain, model);
        verifyZeroInteractions(calculator);
    }

    public void updateInformationZeroLength() throws Exception {
        SimpleChainLikelihoodUpdater updater = new SimpleChainLikelihoodUpdater(calculator);
        when(chain.getSize()).thenReturn(0);
        updater.updateInformation(chain, model);
        verifyZeroInteractions(calculator);
    }

    public void updateLikelihood() throws Exception {
        SimpleChainLikelihoodUpdater updater = new SimpleChainLikelihoodUpdater(calculator);
        when(chain.getSize()).thenReturn(2);
        when(chain.getAt(0)).thenReturn(link1);
        when(chain.getAt(1)).thenReturn(link2);
        when(chain.stateAt(-1)).thenReturn(state1);
        when(chain.stateAt(0)).thenReturn(state2);

        updater.updateLikelihoods(chain, model);
        verify(calculator).updateLikelihood(link1, state1, model);
        verify(calculator).updateLikelihood(link2, state2, model);
    }

    public void updateScore() throws Exception {
        SimpleChainLikelihoodUpdater updater = new SimpleChainLikelihoodUpdater(calculator);
        when(chain.getSize()).thenReturn(2);
        when(chain.getAt(0)).thenReturn(link1);
        when(chain.getAt(1)).thenReturn(link2);
        when(chain.stateAt(-1)).thenReturn(state1);
        when(chain.stateAt(0)).thenReturn(state2);

        updater.updateScores(chain, model);
        verify(calculator).updateLikelihoodAndScore(link1, state1, model);
        verify(calculator).updateLikelihoodAndScore(link2, state2, model);
    }

    public void updateInformation() throws Exception {
        SimpleChainLikelihoodUpdater updater = new SimpleChainLikelihoodUpdater(calculator);
        when(chain.getSize()).thenReturn(2);
        when(chain.getAt(0)).thenReturn(link1);
        when(chain.getAt(1)).thenReturn(link2);
        when(chain.stateAt(-1)).thenReturn(state1);
        when(chain.stateAt(0)).thenReturn(state2);

        updater.updateInformation(chain, model);
        verify(calculator).updateLikelihoodScoreAndInformation(link1, state1, model);
        verify(calculator).updateLikelihoodScoreAndInformation(link2, state2, model);
    }
}