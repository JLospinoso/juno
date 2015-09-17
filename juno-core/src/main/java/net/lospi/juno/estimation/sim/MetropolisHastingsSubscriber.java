package net.lospi.juno.estimation.sim;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.mh.MetropolisHastingsStep;
import net.lospi.juno.estimation.proposal.Modification;
import net.lospi.juno.estimation.proposal.Proposal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class MetropolisHastingsSubscriber {
    private static final Log LOGGER = LogFactory.getLog(SerialChainSimulator.class);
    protected static final long DEFAULT_LOGGING_INTERVAL = 1000L;
    private final ConcurrentMap<Class, AtomicLong> proposals;
    private final ConcurrentMap<Class, AtomicLong> acceptances;
    private final AtomicLong chainLengthSum;
    private double logLikelihoodSum;
    private AtomicLong logUpdateInterval, totalProposalCount;

    public MetropolisHastingsSubscriber() {
        acceptances = new ConcurrentHashMap<Class, AtomicLong>();
        proposals = new ConcurrentHashMap<Class, AtomicLong>();
        logUpdateInterval = new AtomicLong(DEFAULT_LOGGING_INTERVAL);
        totalProposalCount = new AtomicLong(0);
        chainLengthSum = new AtomicLong(0);
    }

    public void send(MetropolisHastingsStep step, Chain chain) {
        updateAcceptanceCounts(step);
        updateChainStatistics(chain);
        if(timeToLog()) {
            writeToLogger();
            clear();
        }
    }

    private void writeToLogger() {
        long count = totalProposalCount.get();
        double chainLengthAverage = (double) chainLengthSum.get()  / (double) count;
        double logLikelihoodAverage = logLikelihoodSum / (double) count;
        StringBuilder builder = new StringBuilder(String.format("[ ] Length:%3.1f LogLike:%7.1f AccRat:",
                chainLengthAverage, logLikelihoodAverage));
        Set<Class> modifications = acceptances.keySet();
        for(Class modification : modifications) {
            long numerator = acceptances.get(modification).get();
            long denominator = proposals.get(modification).get();
            builder.append(String.format(" %3.2f", (double)numerator / (double)denominator));
        }
        LOGGER.debug(builder.toString());
    }

    private void clear() {
        for(Class modification : acceptances.keySet()) {
            acceptances.get(modification).set(0);
            proposals.get(modification).set(0);
        }
        totalProposalCount.set(0);
        chainLengthSum.set(0);
        logLikelihoodSum = 0;
    }

    private void updateChainStatistics(Chain chain) {
        chainLengthSum.addAndGet(chain.getSize());
        for(LikelihoodDerivatives derivatives : chain.getLinkLikelihoodDerivatives()) {
            logLikelihoodSum += derivatives.getLogLikelihood();
        }
    }

    private boolean timeToLog() {
        return totalProposalCount.get() % logUpdateInterval.get() == 0;
    }

    private void updateAcceptanceCounts(MetropolisHastingsStep step) {
        Proposal proposal = step.getProposal();
        Modification modification = proposal.getModification();
        Class<? extends Modification> modificationClass = modification.getClass();
        acceptances.putIfAbsent(modificationClass, new AtomicLong(0));
        proposals.putIfAbsent(modificationClass, new AtomicLong(0));
        if(step.isAccepted()) {
            AtomicLong acceptanceCount = acceptances.get(modificationClass);
            acceptanceCount.incrementAndGet();
        }
        AtomicLong proposalCount = proposals.get(modificationClass);
        proposalCount.incrementAndGet();
        totalProposalCount.incrementAndGet();
    }
}
