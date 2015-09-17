/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.mh;

import net.lospi.juno.elements.Chain;
import net.lospi.juno.estimation.proposal.Proposal;
import net.lospi.juno.model.Model;

public class SimpleMetropolisHastingsStepBuilder {
    Stub with() {
        return new Stub();
    }

    public class Stub {
        private Model model;
        private Chain state;
        private Proposal proposal;
        private Boolean accepted;

        private Stub(){

        }

        public Stub accepted(boolean accepted) {
            this.accepted = accepted;
            return this;
        }

        public Stub proposal(Proposal proposal) {
            this.proposal = proposal;
            return this;
        }

        public Stub state(Chain state) {
            this.state = state;
            return this;
        }

        public Stub model(Model model) {
            this.model = model;
            return this;
        }

        public SimpleMetropolisHastingsStep build() {
            validate();
            return new SimpleMetropolisHastingsStep(model, state, proposal, accepted);
        }

        private void validate() {
            if(model==null){
                throw new IllegalStateException("You must set the model.");
            }
            if(state==null){
                throw new IllegalStateException("You must set the state.");
            }
            if(proposal==null){
                throw new IllegalStateException("You must set the proposal.");
            }
            if(accepted==null){
                throw new IllegalStateException("You must set accepted or not.");
            }
        }
    }
}
