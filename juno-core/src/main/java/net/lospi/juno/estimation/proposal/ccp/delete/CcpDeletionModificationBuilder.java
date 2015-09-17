/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.delete;

import net.lospi.juno.estimation.proposal.ChainSegment;
import net.lospi.juno.estimation.proposal.Modification;

public class CcpDeletionModificationBuilder {
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

        public Modification build() {
            validate();
            return new CcpDeletionModification(start, end, logAlterSelectionProbability, identity, replacement);
        }

        public Stub start(int segmentStartIndex) {
            this.start = segmentStartIndex;
            return this;
        }

        public Stub end(int segmentEndIndex) {
            this.end = segmentEndIndex;
            return this;
        }

        public Stub logAlterSelectionProbability(double logAlterSelectionProbability) {
            this.logAlterSelectionProbability = logAlterSelectionProbability;
            return this;
        }

        private void validate() {
            boolean anyFieldsSet = start != null || end != null || logAlterSelectionProbability != null || replacement != null;
            boolean allFieldsSet = start != null && end != null && logAlterSelectionProbability != null && replacement != null;
            if(anyFieldsSet && identity){
                throw new IllegalStateException("You cannot set properties for an identity modification.");
            }
            if(!allFieldsSet && !identity){
                throw new IllegalStateException("You must set either properties or an identity modification.");
            }
        }

        public Stub replacement(ChainSegment chainSegment) {
            this.replacement = chainSegment;
            return this;
        }
    }
}
