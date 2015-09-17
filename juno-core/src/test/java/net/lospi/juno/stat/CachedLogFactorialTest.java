package net.lospi.juno.stat;

import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

@Test(groups="unit")
public class CachedLogFactorialTest {
    public void testUncachedLogFacValue() throws Exception {
        CachedLogFactorial calculator = new CachedLogFactorial();
        assertThat(calculator.apply(100)).isEqualTo(5050.0);
    }

    public void testCachedLogFacValue() throws Exception {
        CachedLogFactorial calculator = new CachedLogFactorial();
        calculator.apply(100);
        calculator.apply(50);
        calculator.apply(20);
        assertThat(calculator.apply(100)).isEqualTo(5050.0);
    }
}