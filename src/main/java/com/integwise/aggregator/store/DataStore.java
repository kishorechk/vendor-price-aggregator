package com.integwise.aggregator.store;

import java.util.Set;

/**
* All the custom datastores have to implement this interfaxe.
* This interface has CRUD operations (Create, Read, Update and Delete)
* 
* @author Kishor Chukka
* 
*/
public interface DataStore<K, V> {
	V get(K key);
    Set<V> getAll();
    void addOrUpdate(K key, V value);
    void delete(K key);
}