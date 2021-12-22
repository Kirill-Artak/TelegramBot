package com.company.database;

public interface IDatabaseCore<T>{
    boolean trySave(QueryObject<T> query);
    Iterable<T> get(QueryObject<T> query);
    boolean tryUpdate(QueryObject<T> query);
}