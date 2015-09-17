/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.permute;

import net.lospi.juno.estimation.proposal.ChainSegment;

public class PermutationModificationBuilder {
    public Stub with() {
        return new Stub();
    }

    public class Stub {
        private Integer start, end;
        private Double logAlterSelectionProbability;
        private boolean identity = false;
        private ChainSegment replacement;

        public Stub identity() {
            identity = true;
            return this;
        }

        public Stub start(int startingIndex) {
            this.start = startingIndex;
            return this;
        }

        public Stub end(int endingIndex) {
            this.end = endingIndex;
            return this;
        }

        public Stub replacement(ChainSegment replacement) {
            this.replacement = replacement;
            return this;
        }

        public Stub logAlterSelectionProbability(double logAlterSelectionProbability) {
            this.logAlterSelectionProbability = logAlterSelectionProbability;
            return this;
        }

        public PermutationModification build() {
            validate();
            return new PermutationModification(start, end, logAlterSelectionProbability, identity, replacement);
        }

        public boolean anyFieldsSet() {
            if(start != null) {
                return true;
            }
            if(end != null) {
                return true;
            }
            if(logAlterSelectionProbability != null) {
                return true;
            }
            return replacement != null;
        }


        public boolean allFieldsSet() {
            if(start == null) {
                return false;
            }
            if(end == null) {
                return false;
            }
            if(logAlterSelectionProbability == null) {
                return false;
            }
            return replacement != null;
        }

        private void validate() {
            boolean anyFieldsSet = anyFieldsSet();
            boolean allFieldsSet = allFieldsSet();
            if(anyFieldsSet && identity){
                throw new IllegalStateException("You cannot set properties for an identity modification.");
            }
            if(!allFieldsSet && !identity){
                throw new IllegalStateException("You must set either properties or an identity modification.");
            }
        }
    }
}
