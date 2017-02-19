package io.supersimple.notes.data.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public abstract class SimpleCache<K, V> {
    private final Map<K, V> cache;

    public SimpleCache() {
        cache = new HashMap<>();
    }

    public SimpleCache(Map<K, V> cache) {
        this.cache = cache;
    }

    public Maybe<V> get(K id) {
        return Maybe.create(observer -> {
            if (cache.containsKey(id)) {
                observer.onSuccess(cache.get(id));
            }
            observer.onComplete();
        });
    }

    public Maybe<List<V>> list() {
        return Maybe.create(observer -> {
            if (!cache.isEmpty()) {
                List<V> list = new ArrayList<>(cache.values());
                observer.onSuccess(list);
            }
            observer.onComplete();
        });
    }

    public Single<V> save(V value) {
        return Single.create(observer -> {
            cache.put(getId(value), value);
            observer.onSuccess(value);
        });
    }

    public Maybe<V> delete(K id) {
        return Maybe.create(observer -> {
            if (cache.containsKey(id)) {
                observer.onSuccess(cache.remove(id));
            }
            observer.onComplete();
        });
    }

    public Maybe<V> deleteValue(V value) {
        return delete(getId(value));
    }

    public Single<List<V>> deleteAll() {
        return list()
                .flatMapObservable(Observable::fromIterable)
                .map(this::getId)
                .flatMapMaybe(this::delete)
                .toList();
    }

    public void clear() {
        cache.clear();
    }

    public abstract K getId(V value);
}