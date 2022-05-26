package com.github.v1ctu.cache;

import java.util.*;
import java.util.function.Predicate;

public abstract class Cache<K, V> {

    private final Map<K, V> cache = new WeakHashMap<>();

    public void put(K key, V value) {
        cache.put(key, value);
    }

    public void remove(K key) {
        cache.remove(key);
    }

    public V get(Predicate<V> predicate) {
        for (V value : cache.values()) {
            if (predicate.test(value)) return value;
        }

        return null;
    }

    public V get(K key) {
        return cache.get(key);
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public Set<K> getKeys() {
        return cache.keySet();
    }

    public Collection<V> getValues() {
        return cache.values();
    }

}
