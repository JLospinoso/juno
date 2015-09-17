

package net.lospi.juno.util;

import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@Test(groups="unit")
public class JunoTestUtilitiesTest {
    public void resource() throws FileNotFoundException {
        List<String> resourceText = JunoTestUtilities.resource("resource.txt");
        assertThat(resourceText).containsExactly("Resource text.", "Line two");
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void throwsWhenResourceNotFound() throws FileNotFoundException {
        JunoTestUtilities.resource("unknown-resource");
    }
}
