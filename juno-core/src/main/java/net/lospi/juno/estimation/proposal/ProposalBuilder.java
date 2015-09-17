/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal;

public class ProposalBuilder {

    public Stub with(){
        return new Stub();
    }

    public class Stub {
        private Modification modification = null;
        private Double logAcceptanceProbability = null;

        private Stub(){

        }

        public Stub modification(Modification modification){
            this.modification = modification;
            return this;
        }

        public Stub logAcceptanceProbability(double logAcceptanceProbability){
            this.logAcceptanceProbability = logAcceptanceProbability;
            return this;
        }

        public Proposal build(){
            validate();
            return new Proposal(modification, logAcceptanceProbability);
        }

        private void validate() {
            if(modification == null){
                throw new IllegalStateException("You must supply a modification.");
            }
            if(logAcceptanceProbability == null){
                throw new IllegalStateException("You must supply an acceptance probability.");
            }
        }
    }
}
