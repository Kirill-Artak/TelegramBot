package com.company.database;

public interface IDatabaseCore<T>{
    void save(QueryObject<T> query);
    Iterable<T> get(QueryObject<T> query);
    void update(QueryObject<T> query);
}