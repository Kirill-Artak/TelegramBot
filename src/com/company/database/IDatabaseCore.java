package com.company.database;

public interface IDatabaseCore<T>{
    boolean save(QueryObject<T> query);
    Iterable<T> get(QueryObject<T> query);
    boolean update(QueryObject<T> query);
}