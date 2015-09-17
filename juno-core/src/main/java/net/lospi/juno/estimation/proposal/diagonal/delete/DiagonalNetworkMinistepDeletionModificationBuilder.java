/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.delete;

import net.lospi.juno.estimation.proposal.Modification;

public class DiagonalNetworkMinistepDeletionModificationBuilder {
    public Stub with(){
        return new Stub();
    }

    public class Stub {
        private Integer deletionIndex = null;
        private boolean isIdentity = false;
        private Double logAlterSelectionProbability;

        private Stub(){

        }

        public Stub index(int deletionIndex) {
            this.deletionIndex = deletionIndex;
            return this;
        }

        public Stub identity() {
            isIdentity = true;
            return this;
        }

        public Stub logAlterSelectionProbability(double logAlterSelectionProbability){
            this.logAlterSelectionProbability = logAlterSelectionProbability;
            return this;
        }

        public Modification build(){
            validate();
            return new DiagonalNetworkMinistepDeletionModification(deletionIndex, logAlterSelectionProbability, isIdentity);
        }

        private void validate() {
            boolean anyFieldsSet = deletionIndex != null || logAlterSelectionProbability != null;
            boolean allFieldsSet = deletionIndex != null && logAlterSelectionProbability != null;
            if(anyFieldsSet && isIdentity){
                throw new IllegalStateException("You cannot set properties for an identity modification.");
            }
            if(!allFieldsSet && !isIdentity){
                throw new IllegalStateException("You must set either properties or an identity modification.");
            }
        }
    }

}
