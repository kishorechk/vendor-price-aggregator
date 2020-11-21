package com.integwise.aggregator.store;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;

@Component
public class MapDataStore<K, V> implements DataStore<K, V> {

    private final ConcurrentMap<K, V> store;

    public MapDataStore() {
        store = new ConcurrentHashMap<>();
    }

    @Override
	public V get(K key) {
        return store.get(key);
    }

    @Override
	public Set<V> getAll() {
        return new HashSet<V>(store.values());
    }

    @Override
	public void addOrUpdate(K key, V value) {
        store.put(key, value);
    }

    @Override
	public void delete(K key) {
        store.remove(key);
    }
}