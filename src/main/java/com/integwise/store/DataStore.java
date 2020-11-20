package com.integwise.store;

import java.util.Set;

public interface DataStore<K, V> {
	V get(K key);
    Set<V> getAll();
    void addOrUpdate(K key, V value);
    void delete(K key);
}