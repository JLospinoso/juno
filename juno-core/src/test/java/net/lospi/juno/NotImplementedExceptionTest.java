package net.lospi.juno;

import org.testng.annotations.Test;

@Test(groups="unit")
public class NotImplementedExceptionTest {
    @Test(expectedExceptions = NotImplementedException.class)
    public void canConstruct() {
        throw new NotImplementedException();
    }
}