

package net.lospi.juno.elements;

import java.util.SortedSet;

public interface Network {
    boolean value(String actor, String alter);
    void flipTie(String actor, String alter);
    int getActorCount();
    SortedSet<String> getActors();
}
