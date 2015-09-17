

package net.lospi.juno.elements;

import java.util.List;

public interface FileNetworkParser {
    Network parse(List<String> network, String networkName);
}
