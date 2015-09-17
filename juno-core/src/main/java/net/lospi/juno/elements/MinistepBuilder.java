/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.elements;

public class MinistepBuilder {
    public Stub with(){
        return new Stub();
    }

    public class Stub {
        private ActorAspect egoAspect;
        private String alter;

        public Stub egoAspect(ActorAspect egoAspect){
            this.egoAspect = egoAspect;
            return this;
        }

        public Stub alter(String alter) {
            this.alter = alter;
            return this;
        }

        public Ministep build() {
            validate();
            return new Ministep(egoAspect, alter);
        }

        private void validate() {
            boolean allFieldsSet = egoAspect != null && alter != null;
            if(!allFieldsSet){
                throw new IllegalStateException("You must set all fields.");
            }
        }
    }
}
