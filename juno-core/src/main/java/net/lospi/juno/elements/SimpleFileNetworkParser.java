package net.lospi.juno.elements;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;

import java.util.*;

public class SimpleFileNetworkParser implements FileNetworkParser {
    public static final String NETWORK_DELIMITER = ",";
    @Override
    public Network parse(List<String> networkFile, String networkName) {
        int networkFileLength = networkFile.size();
        List<String> actors = new ArrayList<String>(networkFileLength);
        for(int i=0; i<networkFileLength; i++) {
            actors.add(String.format("actor_%02d", i));
        }
        SimpleNetwork result = new SimpleNetwork(ImmutableSortedSet.copyOf(actors));
        for(int i=0; i<networkFileLength; i++) {
            String line = networkFile.get(i);
            String[] tokens = line.split(NETWORK_DELIMITER);
            if(tokens.length != networkFileLength) {
                throw new IllegalArgumentException(String.format("Line %d had %d elements, but there are %d lines in file for network %s",
                        i, tokens.length, networkFileLength, networkFileLength));
            }
            for(int j=0; j<networkFileLength; j++) {
                if(tokens[j].equals("1")) {
                    result.flipTie(actors.get(i), actors.get(j));
                } else if(tokens[j].equals("0")) {
                    // OK, default is 0
                } else {
                    throw new IllegalArgumentException(String.format("Line %d element %d in %s \'%s\' is not 0 or 1.",
                            i, j, networkFile, tokens[j]));
                }
            }
        }
        return result;
    }
}
