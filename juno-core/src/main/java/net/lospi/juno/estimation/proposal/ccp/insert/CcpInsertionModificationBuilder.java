/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.ccp.insert;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.estimation.proposal.ChainSegment;

public class CcpInsertionModificationBuilder {
    public Stub with() {
        return new Stub();
    }

    public class Stub {
        private Integer start, end;
        private Double logAlterSelectionProbability;
        private boolean identity = false;
        private ChainSegment replacement;
        private ActorAspect actorAspect;

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

        public Stub egoAspect(ActorAspect actorAspect) {
            this.actorAspect = actorAspect;
            return this;
        }

        public Stub logAlterSelectionProbability(double logAlterSelectionProbability) {
            this.logAlterSelectionProbability = logAlterSelectionProbability;
            return this;
        }

        public CcpInsertionModification build() {
            validate();
            return new CcpInsertionModification(start, end, logAlterSelectionProbability, identity, replacement, actorAspect);
        }

        private boolean anyFieldsSet() {
            if(start != null) {
                return true;
            }
            if(end != null) {
                return true;
            }
            if(logAlterSelectionProbability != null) {
                return true;
            }
            if(replacement != null) {
                return true;
            }
            return actorAspect != null;
        }

        private boolean allFieldsSet() {
            if(start == null) {
                return false;
            }
            if(end == null) {
                return false;
            }
            if(logAlterSelectionProbability == null) {
                return false;
            }
            if(replacement == null) {
                return false;
            }
            return actorAspect != null;
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
