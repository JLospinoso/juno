/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.stat;

import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.*;

@Test(groups="unit")
public class CachedNaturalLogarithmTest {
    public void testUncachedLnValue() throws Exception {
        CachedNaturalLogarithm calculator = new CachedNaturalLogarithm();
        assertThat(calculator.apply(1234)).isEqualTo(Math.log(1234));
    }

    public void testCachedLnValue() throws Exception {
        CachedNaturalLogarithm calculator = new CachedNaturalLogarithm();
        calculator.apply(12345);
        calculator.apply(1235);
        calculator.apply(345);
        assertThat(calculator.apply(12345)).isEqualTo(Math.log(12345));
    }
}
