/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.elements;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Test(groups="unit")
public class MinistepBuilderTest {
    private ActorAspect egoAspect;
    private String alter;

    @BeforeMethod
    public void setUp(){
        egoAspect = mock(ActorAspect.class);
        alter = "alter";
    }

    @Test(expectedExceptionsMessageRegExp = "You must set all fields\\.", expectedExceptions = IllegalStateException.class)
    public void buildBeforeSettingAnyValues() throws Exception {
        MinistepBuilder builder = new MinistepBuilder();
        builder.with().build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set all fields\\.", expectedExceptions = IllegalStateException.class)
    public void buildWithoutSettingEgoAspect() throws Exception {
        MinistepBuilder builder = new MinistepBuilder();
        builder.with().alter(alter).build();
    }

    @Test(expectedExceptionsMessageRegExp = "You must set all fields\\.", expectedExceptions = IllegalStateException.class)
    public void buildWithoutSettingActorAspect() throws Exception {
        MinistepBuilder builder = new MinistepBuilder();
        builder.with().egoAspect(egoAspect).build();
    }

    public void build() throws Exception {
        MinistepBuilder builder = new MinistepBuilder();
        Ministep result = builder.with().alter(alter).egoAspect(egoAspect).build();
        assertThat(result.getActorAspect()).isEqualTo(egoAspect);
        assertThat(result.getAlter()).isEqualTo(alter);
    }
}