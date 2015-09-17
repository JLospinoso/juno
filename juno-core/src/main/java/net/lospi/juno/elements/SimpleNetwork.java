

package net.lospi.juno.elements;

import org.javatuples.Pair;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

public class SimpleNetwork implements Network {
    private final Set<Pair<String, String>> ties;
    private final SortedSet<String> actors;
    private final int sizeOfActors;

    public SimpleNetwork(SortedSet<String> actors) {
        this.actors = actors;
        ties = new HashSet<Pair<String, String>>();
        sizeOfActors = actors.size();
    }

    @Override
    public boolean value(String ego, String alter) {
        Pair tie = new Pair<String, String>(ego, alter);
        return ties.contains(tie);
    }

    @Override
    public void flipTie(String ego, String alter) {
        if(ego.equals(alter)) {
            return;
        }
        Pair<String, String> tie = new Pair<String, String>(ego, alter);
        if(ties.contains(tie)) {
            ties.remove(tie);
        } else {
            ties.add(tie);
        }
    }

    @Override
    public int getActorCount() {
        return sizeOfActors;
    }

    @Override
    public SortedSet<String> getActors() {
        return actors;
    }
}
