package com.company.database;

public interface IRepository<V, K, I extends Iterable<V>>  {
    void insert(K collectionName, V value, V template);
    I get(K collectionName, V filter);
}
