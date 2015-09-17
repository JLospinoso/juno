package net.lospi.juno.model;

import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

@Test(groups = "unit")
public class GlobalRateEffectTest {

    public void canToString() {
        GlobalRateEffect effect = new GlobalRateEffect("foo");
        String result = effect.toString();
        assertThat(result).isNotNull();
    }
}