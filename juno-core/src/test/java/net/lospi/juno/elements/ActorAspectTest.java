/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.elements;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

@Test(groups="unit")
public class ActorAspectTest {
    String actor;
    String aspect;

    @BeforeMethod
    public void setUp() throws Exception {
        actor = "actor";
        aspect = "aspect";
    }

    public void testGetActor() throws Exception {
        ActorAspect actorAspect = new ActorAspect(actor, aspect);
        assertThat(actorAspect.getActor()).isEqualTo(actor);
    }

    public void testGetAspect() throws Exception {
        ActorAspect actorAspect = new ActorAspect(actor, aspect);
        assertThat(actorAspect.getAspect()).isEqualTo(aspect);
    }

    public void testEquals() throws Exception {
        ActorAspect actorAspect1 = new ActorAspect(actor, aspect);
        ActorAspect actorAspect2 = new ActorAspect(actor, aspect);
        assertThat(actorAspect1.equals(actorAspect2)).isTrue();
    }

    public void testEqualsDifferentActor() throws Exception {
        ActorAspect actorAspect1 = new ActorAspect(actor, aspect);
        ActorAspect actorAspect2 = new ActorAspect("other", aspect);
        assertThat(actorAspect1.equals(actorAspect2)).isFalse();
    }

    public void testEqualsDifferentAspect() throws Exception {
        ActorAspect actorAspect1 = new ActorAspect(actor, aspect);
        ActorAspect actorAspect2 = new ActorAspect(actor, "other");
        assertThat(actorAspect1.equals(actorAspect2)).isFalse();
    }

    public void notEqualNull() throws Exception {
        ActorAspect actorAspect1 = new ActorAspect(actor, aspect);
        assertThat(actorAspect1.equals(null)).isFalse();
    }

    public void notEqualDifferentClass() throws Exception {
        ActorAspect actorAspect1 = new ActorAspect(actor, aspect);
        assertThat(actorAspect1.equals("foo")).isFalse();
    }

    public void same() throws Exception {
        ActorAspect actorAspect1 = new ActorAspect(actor, aspect);
        assertThat(actorAspect1.equals(actorAspect1)).isTrue();
    }

    public void testHashCode() throws Exception {
        ActorAspect actorAspect1 = new ActorAspect(actor, aspect);
        ActorAspect actorAspect2 = new ActorAspect(actor, aspect);
        assertThat(actorAspect1.hashCode()).isEqualTo(actorAspect2.hashCode());
    }

    public void canToString() throws Exception {
        ActorAspect actorAspect1 = new ActorAspect(actor, aspect);
        assertThat(actorAspect1.toString()).isNotNull();
    }
}
