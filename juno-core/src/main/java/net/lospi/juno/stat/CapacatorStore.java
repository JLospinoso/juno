package net.lospi.juno.stat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

public class CapacatorStore<K, V> {
    private static final Log LOG = LogFactory.getLog(CapacatorStore.class);
    private static final int MAX_ELEMENTS       = 10000;
    private static final int LOGGING_INTERVAL   = 10000000;
    private final Map<K, V> store;
    private int misses = 0;
    private int gets = 0;

    public CapacatorStore() {
        this.store = new HashMap<K, V>(MAX_ELEMENTS);
    }

    public V get(K key) {
        gets++;
        V result = store.get(key);
        if (null == result) {
            misses++;
        }
        logCheck();
        return result;
    }

    public void put(K key, V value) {
        spillCheck();
        store.put(key, value);
    }

    private void logCheck() {
        if(gets % LOGGING_INTERVAL == 0) {
            LOG.debug(String.format("[ ] Cache miss rate: %5.4f (%d elements)", (double)misses / (double)gets, store.size()));
        }
    }

    private void spillCheck() {
        if(store.size() >= MAX_ELEMENTS) {
            LOG.debug(String.format("[ ] Purging CapacatorStore."));
            store.clear();
            misses = 0;
            gets = 0;
        }
    }
}
