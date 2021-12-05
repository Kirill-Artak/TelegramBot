package com.company.database;

public record QueryObject<T>(String collection, T query, boolean isUnique, T template) {
}
