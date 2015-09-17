package net.lospi.juno.estimation.sim;

import com.google.common.collect.ImmutableList;
import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.mh.MetropolisHastingsStep;
import net.lospi.juno.estimation.proposal.Modification;
import net.lospi.juno.estimation.proposal.Proposal;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test(groups="unit")
public class MetropolisHastingsSubscriberTest {
    private MetropolisHastingsStep step;
    private Chain chain;
    private long attempts = MetropolisHastingsSubscriber.DEFAULT_LOGGING_INTERVAL * 2;
    private Modification modification;
    private Proposal proposal;
    private LikelihoodDerivatives derivatives;

    @BeforeMethod
    public void setUp() {
        chain = mock(Chain.class);
        step = mock(MetropolisHastingsStep.class);
        modification = mock(Modification.class);
        proposal = mock(Proposal.class);
        derivatives = mock(LikelihoodDerivatives.class);

        when(derivatives.getLogLikelihood()).thenReturn(-1d);
        when(chain.getLinkLikelihoodDerivatives()).thenReturn(ImmutableList.of(derivatives));
        when(step.isAccepted())
                .thenReturn(true)
                .thenReturn(false)
                .thenReturn(true);
        when(chain.getSize()).thenReturn(10);
        when(step.getProposal()).thenReturn(proposal);
        when(proposal.getModification()).thenReturn(modification);
    }

    public void noErrorsAfterLoggingIntervalLapses() {
        MetropolisHastingsSubscriber underStudy = new MetropolisHastingsSubscriber();
        while(attempts-- > 0) {
            underStudy.send(step, chain);
        }
    }
}