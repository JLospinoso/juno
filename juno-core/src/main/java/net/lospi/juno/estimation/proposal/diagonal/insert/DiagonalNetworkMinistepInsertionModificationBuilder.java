/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.proposal.diagonal.insert;

import net.lospi.juno.elements.ActorAspect;
import net.lospi.juno.elements.Ministep;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.estimation.proposal.Modification;

public class DiagonalNetworkMinistepInsertionModificationBuilder {

    public Stub with(){
        return new Stub();
    }

    public class Stub {
        private Integer insertionIndex = null;
        private boolean isIdentity = false;
        private Ministep ministep = null;
        private LikelihoodDerivatives likelihoodDerivatives = null;

        private Stub(){

        }

        public Stub actorAspect(ActorAspect selectedActorAspect){
            this.ministep = new Ministep(selectedActorAspect, selectedActorAspect.getActor());
            return this;
        }

        public Stub index(int insertionIndex) {
            this.insertionIndex = insertionIndex;
            return this;
        }

        public Stub identity() {
            isIdentity = true;
            return this;
        }

        public Stub linkLikelihoodDerivatives(LikelihoodDerivatives likelihoodDerivatives) {
            this.likelihoodDerivatives = likelihoodDerivatives;
            return this;
        }

        public Modification build(){
            validate();
            Double alterSelectionLikelihood = null;
            if(!isIdentity) {
                this.ministep.setLikelihoodDerivatives(likelihoodDerivatives);
                alterSelectionLikelihood = likelihoodDerivatives.getLogLikelihood();
            }
            return new DiagonalNetworkMinistepInsertionModification(insertionIndex, ministep, alterSelectionLikelihood, isIdentity);
        }

        private boolean nonIdentitySupplied() {
            if (insertionIndex != null) {
                return true;
            }
            if (ministep != null) {
                return true;
            }
            return likelihoodDerivatives != null;
        }

        private boolean properlyConfigured() {
            if (insertionIndex == null){
                return false;
            }
            if (ministep == null) {
                return false;
            }
            return likelihoodDerivatives != null;
        }

        private void validate() {
            boolean nonIdentitySupplied = nonIdentitySupplied();
            boolean insertionProperlyConfigured = properlyConfigured();
            if(nonIdentitySupplied && isIdentity){
                throw new IllegalStateException("You cannot set properties for an identity modification.");
            }
            if(!insertionProperlyConfigured && !isIdentity){
                throw new IllegalStateException("You must set either all properties or an identity modification.");
            }
        }
    }
}
